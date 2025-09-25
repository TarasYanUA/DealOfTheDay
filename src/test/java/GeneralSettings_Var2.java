import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.DisableLazyLoadFromBlock;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.AssertsPage;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;
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
        AssertsPage assertsPage = new AssertsPage();
        SoftAssert softAssert = new SoftAssert();

        StPromotions stPromotions = basicPage.navigateTo_Storefront();
        Utils.shiftBrowserTab(1);
        $(".cm-btn-success").click();
        stPromotions.block_DealOfTheDay.scrollIntoCenter();

        //Проверяем, что заголовок промо-акции присутствует в блоке
        assertsPage.assertElementPresence(assertsPage.blockTitle, "in the block!", true);

        //Проверяем, что в блоке присутствует описание
        assertsPage.assertElementPresence(assertsPage.blockDescription, "", true);

        //Проверяем, что присутствует FlipClock счётчик в блоке
        assertsPage.assertElementPresence(assertsPage.flipClock, "in the block!", true);

        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        assertsPage.assertElementPresence(assertsPage.blockButton_More, "", true);

        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        assertsPage.assertElementPresence(assertsPage.blockButton_AllPromotions, "", true);

        //Проверяем, что у блока присутствуют товары
        assertsPage.assertElementPresence(assertsPage.blockProducts, "", true);

        sleep(2000);
        screenshot("200 GeneralSettings_Var2 - Block 'DealOfTheDay'");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.scrollIntoCenter();
        screenshot("202 GeneralSettings_Var2 - Block 'DealOfTheDay' (RTL)");

        //Переходим на страницу списка промо-акций
        stPromotions.blockButton_AllPromotions.click();
        Utils.selectLanguage("ru");

        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(assertsPage.text_OnlyToday.exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");

        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        assertsPage.assertElementPresence(assertsPage.highlight, "on the promotion list page!", true);

        //Проверяем, что "Промо-акций на страницу" присутствует 4 на странице списка промо-акций
        softAssert.assertTrue(assertsPage.promotionsPerPage.size() == 4,
                "Promotions per page are not 4 on the promotion list page!");

        //Проверяем, что присутствует разбиение на страницы (пагинатор) на странице списка промо-акций
        assertsPage.assertElementPresence(assertsPage.pagination, "", true);

        screenshot("205 GeneralSettings_Var2 - Page 'All promotions'");
        Utils.selectLanguage("ar");
        screenshot("207 GeneralSettings_Var2 - Page 'All promotions' (RTL)");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        Utils.selectLanguage("ru");
        stPromotions.promotion_BuyCamera.click();

        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        assertsPage.assertElementPresence(assertsPage.promotionHeaderOnPromoPage, "", true);

        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.flipClock, "on the promotion page!", true);

        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        assertsPage.assertPromotionPeriod_TillTheEndOfCurrentDay();

        //Проверяем, что в промо-акции присутствуют товары
        assertsPage.assertElementPresence(assertsPage.promotionProducts, "on the promotion page!", true);

        sleep(2000);
        screenshot("210 GeneralSettings_Var2 - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("212 GeneralSettings_Var2 - Promotion page (RTL)");
    }
}