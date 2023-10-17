import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

/*
В данном тест-кейсе используются значения по умолчанию:
* Обратный отсчёт до -- Окончания промо-акции
* Промо-акций на страницу --        12
* Показ истекших промо-акций --     да
* Показ ожидаемых промо-акций --    да

Настройки на странице промо-акции:
* Задать период доступности --      да, для поля "Доступна с"
* Отображать счётчик на странице товара --      да (но счётчика не должно быть на странице)
* Отображать счётчик на странице промо-акции--  да (но счётчика не должно быть на странице)
*/

public class GeneralSettings_Var3_AvailableFrom extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var3_AvailableFrom(){
        //Задаём настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_promotion");
        addonSettings.clickAndType_setting_PromotionsPerPage("12");
        if(!addonSettings.setting_ShowExpiredPromotions.isSelected()){
            addonSettings.setting_ShowExpiredPromotions.click(); }
        if(!addonSettings.setting_ShowAwaitingPromotions.isSelected()){
            addonSettings.setting_ShowAwaitingPromotions.click(); }
        addonSettings.button_SaveSettings.click();

        //Задаём настройки на странице промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        //Устанавливаем прошлую дату для поля "Доступна с", чтобы проверить отсутствие счётчика на всех страницах
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setPastDateForSetting_AvailableFrom();
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

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var3_AvailableFrom")
    public void check_GeneralSettings_Var3_AvailableFrom(){
        //Переходим на главную страницу и проверяем блок "Товар дня"
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.hover();
        SoftAssert softAssert = new SoftAssert();
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
        makePause();
        screenshot("250 GeneralSettings_Var3_AvailableFrom - Block 'DealOfTheDay'");
        selectLanguage_RTL();
        stPromotions.block_DealOfTheDay.hover();
        screenshot("252 GeneralSettings_Var3_AvailableFrom - Block 'DealOfTheDay' (RTL)");

        //Переходим на страницу товара
        $(".pd-content-block .ut2-gl__body").click();
        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue(stPromotions.promotionHeader.exists(),
                "There is no promotion header on the product page!");
        //Проверяем, что отсутствует счётчик на странице товара
        softAssert.assertFalse($(".wrapped").exists(),
                "There is a countdown on the product page but shouldn't!");
        screenshot("255 GeneralSettings_Var3_AvailableFrom - Product page (RTL)");
        selectLanguage_RU();
        makePause();
        screenshot("257 GeneralSettings_Var3_AvailableFrom - Product page");

        //Переходим на страницу промо-акции
        $(".pd-promotion__title").click();
        shiftBrowserTab(2);
        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue(stPromotions.promotionHeaderOnPromoPage.exists(),
                "There is no promotion header on the promotion page!");
        //Проверяем, что отсутствует счётчик на странице конкретной промо-акции
        softAssert.assertFalse(stPromotions.countdown.exists(),
                "There is a countdown on the promotion page but shouldn't!");
        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue(!stPromotions.promotionProducts.isEmpty(),
                "There are no products on the promotion page!");
        makePause();
        screenshot("260 GeneralSettings_Var3_AvailableFrom - Promotion page");
        selectLanguage_RTL();
        screenshot("262 GeneralSettings_Var3_AvailableFrom - Promotion page (RTL)");
        softAssert.assertAll();
    }
}