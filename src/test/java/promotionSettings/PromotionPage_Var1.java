package promotionSettings;

import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import generalSettings.TestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

/*
Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Использовать фильтр по товарам --     да
* Скрыть блок товаров --        нет
* Отображать счётчик на странице промо-акции -- да
*/

public class PromotionPage_Var1 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionSettings_Var1(){
        //Задаём настройки промо-акции
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()){  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click();
        }
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click();
        }
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(!promotionSettings.check_UseFilterByProducts.isSelected()){
            promotionSettings.check_UseFilterByProducts.click();
        }
        if (promotionSettings.check_HideProductBlock.isSelected()){
            promotionSettings.check_HideProductBlock.click();
        }
        if(!promotionSettings.check_DisplayLabelInProductLists.isSelected()){
            promotionSettings.check_DisplayLabelInProductLists.click();
        }
        if(!promotionSettings.check_DisplayPromotionInProductLists.isSelected()){
            promotionSettings.check_DisplayPromotionInProductLists.click();
        }
        if(!promotionSettings.check_DisplayCountdownOnProductPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnProductPage.click();
        }
        if(!promotionSettings.check_DisplayCountdownOnPromotionPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnPromotionPage.click();
        }
        csCartSettings.button_Save.click();

        //Задаём настройки модуля
        csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.button_SaveSettings.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionSettings_Var1")
    public void check_PromotionSettings_Var1() {
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.promotion_RacingCard.click();
        csCartSettings.gearWheelOnTop.click();
        promotionSettings.button_PreviewPromotion.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        //Проверяем, что присутствует фильтр товаров на странице промо-акции
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue($x("//div[contains(text(), 'Фильтры товаров')]").exists(),
                "There is no product filters on the promotion page!");
        //Проверяем, что присутствует блок товаров на странице промо-акции
        softAssert.assertTrue($("#promotions_view_pagination_contents").exists(),
                "There is no product block on the promotion page!");
        //Проверяем, что присутствует счётчик на странице промо-акции
        softAssert.assertTrue($(".ab__dotd_promotion-timer").exists(),
                "There is no countdown on the promotion page!");
        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock on the promotion page!");
        screenshot("300 PromotionPage_Var1 - Promotion page");

        //Переходим на страницу товара с промо-акцией
        StPromotions stPromotions = new StPromotions();
        stPromotions.chooseAnyProduct.click();
        //Проверяем, что присутствует счётчик на странице товара
        softAssert.assertTrue($("ab__deal_of_the_day_12").exists(),
                "There is no countdown on the product page!");
        //Проверяем, что присутствует FlipClock счётчик на страницу товара
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock on the product page!");
        screenshot("305 PromotionPage_Var1 - Product page");

        softAssert.assertAll();
    }
}
