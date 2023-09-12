package GeneralSettings;

import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
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
* Обратный отсчёт до -- Окончания дня
* Тип счётчика --       Javascript
* Максимальная высота описания --   250
* Промо-акций на страницу --        12
* Выделение промо-акции --          1
* Количество отображаемых промо-акций в списках товаров --  Не отображать
* Показ истекших промо-акций --     да
* Показ ожидаемых промо-акций --    да
*/

public class GeneralSettings_Var1_Default extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var1_Default(){
        //Задаём настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.tab_Settings.click();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_day");
        addonSettings.setting_CountdownType.selectOptionByValue("javascript");
        addonSettings.clickAndType_setting_MaximumHeightOfDescription("250");
        addonSettings.clickAndType_setting_PromotionsPerPage("12");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("0");
        if(!addonSettings.setting_ShowExpiredPromotions.isSelected()){
            addonSettings.setting_ShowExpiredPromotions.click();
        }
        if(!addonSettings.setting_ShowAwaitingPromotions.isSelected()){
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

        //Устанавливаем прошлую дату в поле "Доступна до", чтобы проверить настройку "Показ истекших промо-акций"
        csCartSettings.navigateToPromotionSettings();
        promotionSettings.promotion_RacingCard.click();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.calendar_ArrowPrevious.shouldBe(Condition.interactable).click();
        promotionSettings.calendar_Day15.click();
        csCartSettings.button_Save.click();

        //Устанавливаем будущую дату в поле "Доступна с", чтобы проверить настройку "Показ ожидаемых промо-акций"
        csCartSettings.navigateToPromotionSettings();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.calendar_ArrowNext.shouldBe(Condition.interactable).click();
        promotionSettings.calendar_Day15.click();
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

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var1_Default")
    public void check_GeneralSettings_Var1_Default(){
        //Переходим на главную страницу и работаем с блоком "Товар дня"
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.hover();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что в блоке присутствует Javascript счётчик
        softAssert.assertTrue($(".js-counter").exists(),
                "Countdown type is not Javascript in the block!");
        screenshot("100 GeneralSettings_Var1_Default - Block 'DealOfTheDay'");

        //Переходим на страницу списка промо-акций
        stPromotions.button_AllPromotions.click();
        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue($x("//div[contains(text(), 'Только сегодня')]").exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");
        //Проверяем, что у промо-акции присутствует текст "До начала" (у промо-акции "Купите фен") - настройка "Показ ожидаемых промо-акций"
        softAssert.assertTrue($x("//div[contains(text(), 'До начала')]").exists(),
                "There is no text 'days left before the start' at promotion on the promotion list page!");
        //Проверяем, что у промо-акции присутствует текст "Акция завершена" (у промо-акции "Гоночный картинг") - настройка "Показ истекших промо-акций"
        softAssert.assertTrue($x("//div[contains(text(), 'Акция завершена')]").exists(),
                "There is no text 'Promotion has expired' at promotion on the promotion list page!");
        //Проверяем, что "Промо-акций на страницу" присутствует не меньше 10 на странице списка промо-акций
        softAssert.assertTrue($$(".ab__dotd_promotions-item").size() >= 10,
                "Promotions per page are less than 10 on the promotion list page!");
        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue($(".ab__dotd_highlight").exists(),
                "There is no Highlighting of the promotion on the promotion list page!");
        screenshot("105 GeneralSettings_Var1_Default - Page 'All promotions'");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        stPromotions.promotion_BuyCamera.click();
        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue($(".ab__dotd_promotion-main_info").exists(),
                "There is no promotion header on the promotion page!");
        //Проверяем, что "Максимальная высота описания" -- 250
        softAssert.assertTrue($("div[style*='max-height: 250px']").exists(),
                "Maximum height of description is not 250 px on the promotion page!");
        //Проверяем, что кнопка "Больше" присутствует в описании промо-акции
        softAssert.assertTrue($$(".ab__dotd_more").size() >= 1,
                "There is no button 'More' at the promotion description on the promotion page!");
        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        softAssert.assertTrue(promotionDate.contains(currentDate), "Promotion period is not till the end of the current day!");
        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue($$(".ut2-gl__item").size() >= 1,
                "There are no products on the promotion page!");
        makePause();
        screenshot("110 GeneralSettings_Var1_Default - Promotion page");

        //Переходим на страницу товара с промо-акцией и проверяем все шаблоны страницы товара
        stPromotions.chooseAnyProduct.click();
        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue($(".ab__deal_of_the_day").exists(),
                "There is no promotion header on the product page!");
        makePause();
        screenshot("112 GeneralSettings_Var1_Default - Product page, Default");
        selectLanguage_RTL();
        screenshot("114 GeneralSettings_Var1_Default - Product page, Default (RTL)");
        String productCode = $("span[id^='product_code_']").getText(); //Берём код товара
        shiftBrowserTab(0);
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        csCartSettings.productTemplate.selectOptionByValue("bigpicture_template");
        goToProductPage(2);
        screenshot("116 GeneralSettings_Var1_Default - Product page, BigPicture");
        selectLanguage_RTL();
        screenshot("118 GeneralSettings_Var1_Default - Product page, BigPicture (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_flat_template");
        goToProductPage(3);
        screenshot("120 GeneralSettings_Var1_Default - Product page, BigPictureFlat");
        selectLanguage_RTL();
        screenshot("122 GeneralSettings_Var1_Default - Product page, BigPictureFlat (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        goToProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("124 GeneralSettings_Var1_Default - Product page, Gallery");
        selectLanguage_RTL();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("126 GeneralSettings_Var1_Default - Product page, Gallery (RTL)");
        selectProductTemplate("abt__ut2_three_columns_template");
        goToProductPage(5);
        screenshot("128 GeneralSettings_Var1_Default - Product page, Three-columned");
        selectLanguage_RTL();
        screenshot("130 GeneralSettings_Var1_Default - Product page, Three-columned (RTL)");
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