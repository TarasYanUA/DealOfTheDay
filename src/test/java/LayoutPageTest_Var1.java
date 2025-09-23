import adminPanel.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Работаем с макетом Light v2 и блоком "Мульти товар дня"!
Настройки модуля:
* Тип счётчика --       FlipClock

Настройки промо-акции "Фен Valera":
* Задать период доступности --  да, для поля "Доступна до"

Настройки блока:
* Количество элементов --           5
* Спрятать кнопку добавления товара в корзину --    нет
* Отображать счётчик промо-акции -- да
*/

public class LayoutPageTest_Var1 extends TestRunner implements DisableLazyLoadFromBlock {
    @Test(priority = 1)
    public void setConfigurations_MultiBlockTest_Var1() {
        BasicPage basicPage = new BasicPage();
        //Задаём настройки CS-Cart
        basicPage.navigateTo_AppearanceSettingsAndQuickViewOn();

        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции "Фен Valera"
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        //Берём ID данной промо-акции
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String [] split = currentUrl.split("id=");
        String promotionID = split[1];
        //promotionSettings.clickAndType_field_DetailedDescription();
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        promotionSettings.tab_Conditions.scrollIntoCenter().click();
        promotionSettings.button_AddProductsToCondition.click();
        $(".ui-dialog-title").shouldBe(Condition.visible);
        promotionSettings.field_SearchProduct.setValue("Samsung серии 3 15.6\" 300V5A");
        promotionSettings.field_SearchProduct.pressEnter();
        promotionSettings.checkProductToCondition.click();
        promotionSettings.button_AddAndClose.click();
        basicPage.button_Save.click();

        //Работаем с блоком "Мульти товар дня"
        LayoutPage layoutPage = basicPage.navigateToSectionLayouts();
        layoutPage.layout_LightV2.click();
        layoutPage.setLayoutAsDefault();
        layoutPage.layout_TabHomePage.click();
        layoutPage.switchOffBlock_DealOfTheDay();
        //Создаём блок "Мульти Товар дня"
        if (!$x("//div[@title=\"MultiBlock - AutoTest\"]").exists()) {
            layoutPage.addNewBlock();
            basicPage.popupWindow.shouldBe(Condition.enabled);
            layoutPage.tab_CreateNewBlock.click();
            layoutPage.multiBlock.click();
            $("#ui-id-2").shouldBe(Condition.enabled);
            layoutPage.blockName.setValue("MultiBlock - AutoTest");
            layoutPage.tab_Content.click();
            layoutPage.button_AddPromotionsToBlock.click();
            $("#ui-id-3").shouldBe(Condition.enabled);
            $(("input[id^='checkbox_id_" + promotionID)).click();
            layoutPage.button_AddAndCloseSelectedPromotions.click();
            layoutPage.button_CreateBlock.click();
        }
        //Задаём настройки блоку "Мульти Товар дня"
        layoutPage.blockProperties.click();
        basicPage.popupWindow.shouldBe(Condition.enabled);
        layoutPage.tab_BlockSettings.click();
        Utils.setCheckboxState(layoutPage.setting_DoNotScrollAutomatically, true);
        layoutPage.setting_ItemQuantity.setValue("5");
        Utils.setCheckboxState(layoutPage.setting_HideAddToCart, false);
        Utils.setCheckboxState(layoutPage.setting_DisplayPromotionCountdown, true);
        layoutPage.button_SaveBlockProperties.click();
        disableLazyLoadFromBlock("MultiBlock - AutoTest");
    }

    @Test(priority = 2, dependsOnMethods = "setConfigurations_MultiBlockTest_Var1")
    public void check_Block(){
        BasicPage basicPage = new BasicPage();
        StPromotions stPromotions = basicPage.navigateTo_Storefront();
        Utils.shiftBrowserTab(1);
        $(".cm-btn-success").click();
        stPromotions.block_DealOfTheDay.scrollIntoCenter().hover();

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue(stPromotions.blockTitle.exists(),
                "There is no title of the promotion in the multi block!");

        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue(stPromotions.blockDescription.exists(),
                "There is no description of the promotion in the multi block!");

        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the multi block!");

        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the multi block!");

        //Проверяем, что в блоке присутствует счётчик Flipclock
        softAssert.assertTrue(stPromotions.flipClock.exists(),
                "The countdown is not FlipClock in the multi block!");

        //Проверяем, что в блоке присутствует цена
        softAssert.assertTrue(!$$(".ab__deal_of_the_day .ty-list-price.ty-nowrap").isEmpty(),
                "There is no price at products in the multi block!");

        //Проверяем, что в блоке отсутствует кнопка быстрого просмотра
        softAssert.assertTrue($$(".ab__deal_of_the_day a[data-ca-target-id=\"product_quick_view\"]").isEmpty(),
                "There is a quick view button at the products in the multi block but shouldn't!");

        //Проверяем, что в блоке присутствует кнопка "Купить"
        softAssert.assertTrue(!$$(".ab__deal_of_the_day .ut2-icon-use_icon_cart").isEmpty(),
                "There is no button 'Add to cart' at the products in the multi block!");

        //Проверяем, что у блока 6 товаров
        softAssert.assertTrue(stPromotions.blockProducts.size() == 6,
                "There are not 6 products in the multi block!");

        sleep(2000);
        screenshot("800 MultiBlockTest_Var1 - Multi block");
        Utils.selectLanguage("ar");
        stPromotions.block_DealOfTheDay.scrollIntoCenter().hover();
        screenshot("805 MultiBlockTest_Var1 - Multi block (RTL)");
        softAssert.assertAll();
    }
}