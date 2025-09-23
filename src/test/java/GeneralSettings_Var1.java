import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.DisableLazyLoadFromBlock;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

public class GeneralSettings_Var1 extends TestRunner implements DisableLazyLoadFromBlock {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var1() {
        disableLazyLoadFromBlock("AB: Товар дня");
        //Задаём настройки модуля
        BasicPage basicPage = new BasicPage();
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_day");
        addonSettings.setting_CountdownType.selectOptionByValue("javascript");
        addonSettings.setting_MaximumHeightOfDescription.setValue("250");
        addonSettings.setting_PromotionsPerPage.setValue("12");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("0");
        Utils.setCheckboxState(addonSettings.setting_ShowExpiredPromotions, true);
        Utils.setCheckboxState(addonSettings.setting_ShowAwaitingPromotions, true);
        addonSettings.button_SaveSettings.click();

        //Задаём настройки на странице промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        promotionSettings.clickAndType_field_DetailedDescription(); //Чтобы проверить настройку "Максимальная высота описания"
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, true);
        //Устанавливаем сегодняшнюю дату для поля "Доступна до", чтобы проверить настройку "Обратный отсчёт до"
        promotionSettings.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        basicPage.button_Save.click();
        //Устанавливаем прошлую дату в поле "Доступна до", чтобы проверить настройку "Показ истекших промо-акций"
        basicPage.navigateTo_PromotionSettings();
        promotionSettings.promotion_RacingCard.click();
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, true);
        promotionSettings.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.calendar_ArrowPrevious.shouldBe(Condition.interactable).click();
        promotionSettings.calendar_Day15.click();
        basicPage.button_Save.click();
        //Устанавливаем будущую дату в поле "Доступна с", чтобы проверить настройку "Показ ожидаемых промо-акций"
        basicPage.navigateTo_PromotionSettings();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, true);
        promotionSettings.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.calendar_ArrowNext.shouldBe(Condition.interactable).click();
        promotionSettings.calendar_Day15.click();
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.scrollIntoView(false).click();
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, false);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnPromotionPage, true);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var1")
    public void check_GeneralSettings_Var1() {
        //Переходим на главную страницу и проверяем блок "Товар дня"
        BasicPage basicPage = new BasicPage();
        StPromotions stPromotions = basicPage.navigateTo_Storefront();
        Utils.shiftBrowserTab(1);
        $(".cm-btn-success").click();
        stPromotions.block_DealOfTheDay.scrollIntoCenter();
        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue(stPromotions.blockTitle.exists(),
                "There is no title of the promotion in the block!");

        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue(stPromotions.blockDescription.exists(),
                "There is no description of the promotion in the block!");

        //Проверяем, что в блоке присутствует Javascript счётчик
        softAssert.assertTrue(stPromotions.javaClock.exists(),
                "Countdown type is not Javascript in the block!");

        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the block!");

        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the block!");

        //Проверяем, что у блока присутствуют товары
        softAssert.assertTrue(!stPromotions.blockProducts.isEmpty(),
                "There are no products in the block!");

        sleep(2000);
        screenshot("100 GeneralSettings_Var1 - Block 'DealOfTheDay'");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.scrollIntoCenter();
        screenshot("102 GeneralSettings_Var1 - Block 'DealOfTheDay' (RTL)");

        //Переходим на страницу списка промо-акций
        stPromotions.blockButton_AllPromotions.click();
        Utils.selectLanguage("ru");

        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.text_OnlyToday.exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");

        //Проверяем, что у промо-акции присутствует текст "До начала" (у промо-акции "Купите фен") - настройка "Показ ожидаемых промо-акций"
        softAssert.assertTrue(stPromotions.text_DaysLeftBeforeStart.exists(),
                "There is no text 'days left before the start' at promotion on the promotion list page!");

        //Проверяем, что у промо-акции присутствует текст "Акция завершена" (у промо-акции "Гоночный картинг") - настройка "Показ истекших промо-акций"
        softAssert.assertTrue(stPromotions.text_PromotionHasExpired.exists(),
                "There is no text 'Promotion has expired' at promotion on the promotion list page!");

        //Проверяем, что "Промо-акций на страницу" присутствует не меньше 10 на странице списка промо-акций
        softAssert.assertTrue(stPromotions.promotionsPerPage.size() >= 10,
                "Promotions per page are less than 10 on the promotion list page!");

        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.highlight.exists(),
                "There is no Highlighting of the promotion on the promotion list page!");

        screenshot("105 GeneralSettings_Var1 - Page 'All promotions'");
        Utils.selectLanguage("ar");
        screenshot("107 GeneralSettings_Var1 - Page 'All promotions' (RTL)");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        Utils.selectLanguage("ru");
        stPromotions.promotion_BuyCamera.click();

        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue(stPromotions.promotionHeaderOnPromoPage.exists(),
                "There is no promotion header on the promotion page!");

        //Проверяем, что "Максимальная высота описания" -- 250
        softAssert.assertTrue($("div[style*='max-height: 250px']").exists(),
                "Maximum height of description is not 250 px on the promotion page!");

        //Проверяем, что кнопка "Больше" присутствует в описании промо-акции
        softAssert.assertTrue(!$$(".ab__dotd_more").isEmpty(),
                "There is no button 'More' at the promotion description on the promotion page!");

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(": ");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate, "Promotion period is not till the end of the current day!");

        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue(!stPromotions.promotionProducts.isEmpty(),
                "There are no products on the promotion page!");

        sleep(2000);
        screenshot("110 GeneralSettings_Var1 - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("112 GeneralSettings_Var1 - Promotion page (RTL)");
        softAssert.assertAll();
    }
}