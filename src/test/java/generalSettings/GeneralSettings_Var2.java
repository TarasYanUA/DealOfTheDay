package generalSettings;

import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/*
В данном тест-кейсе используются значения по умолчанию:
* Обратный отсчёт до -- Окончания промо-акции
* Тип счётчика --       FlipClock
* Максимальная высота описания --   400
* Промо-акций на страницу --        4
* Выделение промо-акции --          1
* Количество отображаемых промо-акций в списках товаров --  2
* Показ истекших промо-акций --     нет
* Показ ожидаемых промо-акций --    нет
*/

public class GeneralSettings_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var2(){
        //Задаём настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_promotion");
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.clickAndType_setting_MaximumHeightOfDescription("400");
        addonSettings.clickAndType_setting_PromotionsPerPage("4");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        if(addonSettings.setting_ShowExpiredPromotions.isSelected()){
            addonSettings.setting_ShowExpiredPromotions.click();
        }
        if(addonSettings.setting_ShowAwaitingPromotions.isSelected()){
            addonSettings.setting_ShowAwaitingPromotions.click();
        }
        addonSettings.button_SaveSettings.click();

        //Работаем на странице редактирования промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        promotionSettings.clickAndType_field_DetailedDescription(); //Чтобы проверить настройку "Максимальная высота описания"
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        //Устанавливаем сегодняшнюю дату для поля "Доступна до", чтобы проверить настройку "Обратный отсчёт до"
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        csCartSettings.button_Save.click();
    }
    public void clearBothFieldsAvailable(){
        PromotionSettings promotionSettings = new PromotionSettings();
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.setting_AvailableFrom.clear();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.setting_AvailableTill.clear();
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var2")
    public void check_GeneralSettings_Var2(){
        //Переходим на главную страницу и проверяем блок "Товар дня"
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.hover();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что присутствует FlipClock счётчик в блоке
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock in the block!");
        screenshot("200 GeneralSettings_Var2 - Block 'DealOfTheDay'");

        //Переходим на страницу списка промо-акций
        stPromotions.button_AllPromotions.click();
        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue($x("//div[contains(text(), 'Только сегодня')]").exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");
        //Проверяем, что "Промо-акций на страницу" присутствует 4 на странице списка промо-акций
        softAssert.assertTrue($$(".ab__dotd_promotions-item").size() == 4,
                "Promotions per page are not 4 on the promotion list page!");
        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue($(".ab__dotd_highlight").exists(),
                "There is no Highlighting of the promotion on the promotion list page!");
        screenshot("205 GeneralSettings_Var2 - Page 'All promotions'");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        stPromotions.promotion_BuyCamera.click();
        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue($(".ab__dotd_promotion-main_info").exists(),
                "There is no promotion header on the promotion page!");
        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock on the promotion page!");
        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(":");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate, "Promotion period is not till the end of the current day!");
        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue($$(".ut2-gl__item").size() >= 1,
                "There are no products on the promotion page!");
        makePause();
        screenshot("210 GeneralSettings_Var2 - Promotion page");

        //Переходим на страницу товара с промо-акцией и проверяем все шаблоны страницы товара
        stPromotions.chooseAnyProduct.click();
        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue($(".ab__deal_of_the_day").exists(),
                "There is no promotion header on the product page!");
        //Проверяем, что присутствует FlipClock счётчик на странице товара
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock on the product page!");
        makePause();
        screenshot("212 GeneralSettings_Var2 - Product page, Default");
        selectLanguage_RTL();
        screenshot("214 GeneralSettings_Var2 - Product page, Default (RTL)");
        String productCode = $("span[id^='product_code_']").getText(); //Берём код товара
        shiftBrowserTab(0);
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        csCartSettings.productTemplate.selectOptionByValue("bigpicture_template");
        goToProductPage(2);
        screenshot("216 GeneralSettings_Var2 - Product page, BigPicture");
        selectLanguage_RTL();
        screenshot("218 GeneralSettings_Var2 - Product page, BigPicture (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_flat_template");
        goToProductPage(3);
        screenshot("220 GeneralSettings_Var2 - Product page, BigPictureFlat");
        selectLanguage_RTL();
        screenshot("222 GeneralSettings_Var2 - Product page, BigPictureFlat (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        goToProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("224 GeneralSettings_Var2 - Product page, Gallery");
        selectLanguage_RTL();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("226 GeneralSettings_Var2 - Product page, Gallery (RTL)");
        selectProductTemplate("abt__ut2_three_columns_template");
        goToProductPage(5);
        screenshot("228 GeneralSettings_Var2 - Product page, Three-columned");
        selectLanguage_RTL();
        screenshot("230 GeneralSettings_Var2 - Product page, Three-columned (RTL)");
        softAssert.assertAll();
    }

    public void selectProductTemplate(String templateValue) {
        shiftBrowserTab(0);
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.productTemplate.selectOptionByValue(templateValue);
    }

    public void goToProductPage(int tabNumber){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_Preview.click();
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
        makePause();
    }
}