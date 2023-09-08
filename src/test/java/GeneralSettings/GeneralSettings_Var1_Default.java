package GeneralSettings;

import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;

import static com.codeborne.selenide.Selenide.*;

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
        //Проверяем, что "Максимальная высота описания" -- 250
        softAssert.assertTrue($("div[style*='max-height: 250px']").exists(),
                "Maximum height of description is not 250 px on the promotion page!");
        //Проверяем, что кнопка "Больше" присутствует в описании промо-акции
        softAssert.assertTrue($(".ab__dotd_more").exists(),
                "There is no button 'More' at the promotion description on the promotion page!");
        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue($$(".ut2-gl__item").size() >= 1,
                "There is no products on the promotion page!");
    }
}