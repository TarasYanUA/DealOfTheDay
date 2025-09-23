import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.DisableLazyLoadFromBlock;
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

public class GeneralSettings_Var2 extends TestRunner implements DisableLazyLoadFromBlock {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var2(){
        disableLazyLoadFromBlock("AB: Товар дня");
        //Задаём настройки модуля
        BasicPage basicPage = new BasicPage();
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_promotion");
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.setting_MaximumHeightOfDescription.setValue("400");
        addonSettings.setting_PromotionsPerPage.setValue("4");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        Utils.setCheckboxState(addonSettings.setting_ShowExpiredPromotions, false);
        Utils.setCheckboxState(addonSettings.setting_ShowAwaitingPromotions, false);
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
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var2")
    public void check_GeneralSettings_Var2(){
        //Переходим на главную страницу и проверяем блок "Товар дня"
        BasicPage basicPage = new BasicPage();
        SoftAssert softAssert = new SoftAssert();

        StPromotions stPromotions = basicPage.navigateTo_Storefront();
        Utils.shiftBrowserTab(1);
        $(".cm-btn-success").click();
        stPromotions.block_DealOfTheDay.hover();

        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue(stPromotions.blockTitle.exists(),
                "There is no title of the promotion in the block!");

        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue(stPromotions.blockDescription.exists(),
                "There is no description of the promotion in the block!");

        //Проверяем, что присутствует FlipClock счётчик в блоке
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock in the block!");

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
        screenshot("200 GeneralSettings_Var2 - Block 'DealOfTheDay'");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.hover();
        screenshot("202 GeneralSettings_Var2 - Block 'DealOfTheDay' (RTL)");

        //Переходим на страницу списка промо-акций
        stPromotions.blockButton_AllPromotions.click();
        Utils.selectLanguage("ru");

        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.text_OnlyToday.exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");

        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.highlight.exists(),
                "There is no Highlighting of the promotion on the promotion list page!");

        //Проверяем, что "Промо-акций на страницу" присутствует 4 на странице списка промо-акций
        softAssert.assertTrue(stPromotions.promotionsPerPage.size() == 4,
                "Promotions per page are not 4 on the promotion list page!");

        //Проверяем, что присутствует разбиение на страницы (пагинатор) на странице списка промо-акций
        softAssert.assertTrue($("#ut2_pagination_block_bottom").exists(),
                "There is no pagination on the promotion list page!");
        screenshot("205 GeneralSettings_Var2 - Page 'All promotions'");
        Utils.selectLanguage("ar");
        screenshot("207 GeneralSettings_Var2 - Page 'All promotions' (RTL)");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        Utils.selectLanguage("ru");
        stPromotions.promotion_BuyCamera.click();

        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue(stPromotions.promotionHeaderOnPromoPage.exists(),
                "There is no promotion header on the promotion page!");

        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        softAssert.assertTrue(stPromotions.flipClock.exists(),
                "Countdown type is not FlipClock on the promotion page!");

        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(": ");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate,
                "Promotion period is not till the end of the current day!");

        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue(!stPromotions.promotionProducts.isEmpty(),
                "There are no products on the promotion page!");
        sleep(2000);
        screenshot("210 GeneralSettings_Var2 - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("212 GeneralSettings_Var2 - Promotion page (RTL)");
        softAssert.assertAll();
    }
}