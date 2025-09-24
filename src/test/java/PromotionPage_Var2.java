import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

/*
Настройки модуля:
- Количество отображаемых промо-акций в списках товаров --  2
- Выделение промо-акции --  1

Настройки на странице промо-акции:
* Задать период доступности --  да
* Не применять другие промо-акции --    выкл
* Использовать фильтр по товарам --     нет
* Скрыть блок товаров --        да
* Отображать счётчик на странице промо-акции -- нет
*/

public class PromotionPage_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionPage_Var2(){
        BasicPage basicPage = new BasicPage();
        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        Utils.setCheckboxState(promotionSettings.setting_StopOtherRules, false);
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.scrollIntoCenter().click();
        Utils.setCheckboxState(promotionSettings.check_UseFilterByProducts, false);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, true);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, true);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnPromotionPage, false);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var2")
    public void check_PromotionPage_Var2() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        basicPage.navigateToStorefront(1);

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что отсутствует фильтр товаров на странице промо-акции
        StPromotions stPromotions = new StPromotions();
        softAssert.assertFalse(stPromotions.filterByProducts.exists(),
                "There are the product filters on the promotion page but shouldn't!");

        //Проверяем, что отсутствует блок товаров на странице промо-акции
        softAssert.assertFalse(stPromotions.productBlock.exists(),
                "There is a product block on the promotion page but shouldn't!");

        //Проверяем, что отсутствует счётчик на странице промо-акции
        softAssert.assertFalse(stPromotions.countdown.exists(),
                "There is a countdown on the promotion page but shouldn't!");

        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(": ");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate,
                "Promotion period is not till the end of the current day!");

        screenshot("400 PromotionPage_Var2 - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("405 PromotionPage_Var2 - Promotion page (RTL)");
        softAssert.assertAll();
    }
}