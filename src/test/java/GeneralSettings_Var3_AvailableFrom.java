import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.DisableLazyLoadFromBlock;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
В данном тест-кейсе используются значения по умолчанию:
* Обратный отсчёт до -- Окончания промо-акции
* Показ истекших промо-акций --     да
* Показ ожидаемых промо-акций --    да

Настройки на странице промо-акции:
* Задать период доступности --      да, для поля "Доступна с"
* Отображать счётчик на странице товара --      да (но счётчика не должно быть на странице)
* Отображать счётчик на странице промо-акции--  да (но счётчика не должно быть на странице)
*/

public class GeneralSettings_Var3_AvailableFrom extends TestRunner implements DisableLazyLoadFromBlock {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var3_AvailableFrom() {
        disableLazyLoadFromBlock("AB: Товар дня");
        //Задаём настройки модуля
        BasicPage basicPage = new BasicPage();
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_promotion");
        Utils.setCheckboxState(addonSettings.setting_ShowExpiredPromotions, true);
        Utils.setCheckboxState(addonSettings.setting_ShowAwaitingPromotions, true);
        addonSettings.button_SaveSettings.click();

        //Задаём настройки на странице промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, true);
        //Устанавливаем прошлую дату для поля "Доступна с", чтобы проверить отсутствие счётчика на всех страницах
        promotionSettings.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setPastDateForSetting_AvailableFrom();
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var3_AvailableFrom")
    public void check_GeneralSettings_Var3_AvailableFrom() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        //Переходим на страницу промо-акции
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        basicPage.saveAndGoToStorefront_ProductPage(1);
        StPromotions stPromotions = new StPromotions();

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue(stPromotions.promotionHeaderOnPromoPage.exists(),
                "There is no promotion header on the promotion page!");

        //Проверяем, что отсутствует счётчик на странице конкретной промо-акции
        softAssert.assertFalse(stPromotions.countdown.exists(),
                "There is a countdown on the promotion page but shouldn't!");

        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue(!stPromotions.promotionProducts.isEmpty(),
                "There are no products on the promotion page!");

        sleep(2000);
        screenshot("250 GeneralSettings_Var3_AvailableFrom - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("252 GeneralSettings_Var3_AvailableFrom - Promotion page (RTL)");

        //Переходим на страницу товара
        $(".ut2-gl__body").click();

        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue(stPromotions.promotionHeader.exists(),
                "There is no promotion header on the product page!");

        //Проверяем, что отсутствует счётчик на странице товара
        softAssert.assertFalse($(".wrapped").exists(),
                "There is a countdown on the product page but shouldn't!");

        screenshot("255 GeneralSettings_Var3_AvailableFrom - Product page (RTL)");
        Utils.selectLanguage("ru");
        sleep(2000);
        screenshot("257 GeneralSettings_Var3_AvailableFrom - Product page");

        //Переходим на главную страницу и проверяем блок
        $(".ty-breadcrumbs__a").click();
        stPromotions.block_DealOfTheDay.hover();

        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue(stPromotions.blockTitle.exists(),
                "There is no title of the promotion in the block!");

        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue(stPromotions.blockDescription.exists(),
                "There is no description of the promotion in the block!");

        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the block!");

        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the block!");

        //Проверяем, что у блока присутствуют товары
        softAssert.assertTrue(!stPromotions.blockProducts.isEmpty(),
                "There are no products in the block!");

        //Проверяем, что у блока отсутствует счётчик
        softAssert.assertFalse(stPromotions.countdown.exists(),
                "There is a countdown in the block but shouldn't!");

        sleep(2000);
        screenshot("260 GeneralSettings_Var3_AvailableFrom - Block 'DealOfTheDay'");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.hover();
        screenshot("262 GeneralSettings_Var3_AvailableFrom - Block 'DealOfTheDay' (RTL)");
        softAssert.assertAll();
    }
}