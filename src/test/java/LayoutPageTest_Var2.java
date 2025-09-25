import adminPanel.*;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.AssertsPage;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Работаем с макетом Light v2 и блоком "Мульти товар дня"!
Настройки модуля:
* Тип счётчика --       Javascript

Настройки промо-акции "Фен Valera":
* Задать период доступности --  да, для поля "Доступна до"

Настройки блока:
* Количество элементов --           4
* Спрятать кнопку добавления товара в корзину --    да
* Отображать счётчик промо-акции -- да
*/

public class LayoutPageTest_Var2 extends TestRunner implements DisableLazyLoadFromBlock {
    @Test(priority = 1)
    public void setConfigurations_MultiBlockTest_Var2() {
        BasicPage basicPage = new BasicPage();
        //Задаём настройки CS-Cart
        basicPage.navigateTo_AppearanceSettingsAndQuickViewOn();

        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("javascript");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции "Фен Valera"
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        //Берём ID промо-акции
        String promotionID = promotionSettings.takePromotionID();
        //promotionSettings.clickAndType_field_DetailedDescription();
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        basicPage.button_Save.click();

        //Работаем с блоком "Мульти товар дня"
        LayoutPage layoutPage = basicPage.navigateToSectionLayouts();
        layoutPage.layout_LightV2.click();
        layoutPage.setLayoutAsDefault();
        layoutPage.layout_TabHomePage.click();
        layoutPage.switchOffBlock_DealOfTheDay();
        //Создаём блок "Мульти Товар дня"
        layoutPage.createBlock_MultiDealOfTheDay(promotionID);
        //Задаём настройки блоку "Мульти Товар дня"
        layoutPage.blockProperties.click();
        basicPage.popupWindow.shouldBe(Condition.enabled);
        layoutPage.tab_BlockSettings.click();
        Utils.setCheckboxState(layoutPage.setting_DoNotScrollAutomatically, true);
        layoutPage.setting_ItemQuantity.setValue("4");
        Utils.setCheckboxState(layoutPage.setting_HideAddToCart, true);
        Utils.setCheckboxState(layoutPage.setting_DisplayPromotionCountdown, true);
        layoutPage.button_SaveBlockProperties.click();
        disableLazyLoadFromBlock("MultiBlock - AutoTest");
    }

    @Test(priority = 2, dependsOnMethods = "setConfigurations_MultiBlockTest_Var2")
    public void check_Block(){
        BasicPage basicPage = new BasicPage();
        StPromotions stPromotions = basicPage.navigateTo_Storefront();
        Utils.shiftBrowserTab(1);
        $(".cm-btn-success").click();
        stPromotions.block_DealOfTheDay.scrollIntoCenter();

        AssertsPage assertsPage = new AssertsPage();
        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что заголовок промо-акции присутствует в блоке
        assertsPage.assertElementPresence(assertsPage.blockTitle, "in the multi block!", true);

        //Проверяем, что в блоке присутствует описание
        assertsPage.assertElementPresence(assertsPage.blockDescription, "", true);

        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the multi block!");

        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the multi block!");

        //Проверяем, что в блоке присутствует счётчик Javascript
        assertsPage.assertElementPresence(assertsPage.javaClock, "in the multi block!", true);

        //Проверяем, что в блоке присутствует кнопка быстрого просмотра
        softAssert.assertTrue(!$$(".ab__deal_of_the_day a[data-ca-target-id=\"product_quick_view\"]").isEmpty(),
                "There is a quick view button at the products in the multi block but shouldn't!");

        //Проверяем, что в блоке отсутствует кнопка "Купить"
        softAssert.assertTrue($$(".ab__deal_of_the_day .ut2-icon-use_icon_cart").isEmpty(),
                "There is a button 'Add to cart' at the products in the multi block but shouldn't!");

        sleep(2000);
        screenshot("900 MultiBlockTest_Var2 - Multi block");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.scrollIntoCenter();
        screenshot("905 MultiBlockTest_Var2 - Multi block (RTL)");
    }
}