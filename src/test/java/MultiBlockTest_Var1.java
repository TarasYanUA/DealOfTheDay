import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.MultiBlock;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

/*
Работаем с макетом Light v2 и блоком "Мульти товар дня"!
Настройки модуля:
* Тип счётчика --       FlipClock

Настройки промо-акции "Фен Valera":
* Задать период доступности --  да, для поля "Доступна до"

Настройки блока:
* Количество элементов --           6
* Спрятать кнопку добавления товара в корзину --    нет
* Отображать счётчик промо-акции -- да
*/

public class MultiBlockTest_Var1 extends TestRunner {
    @Test(priority = 1)
    public void setConfigurations_MultiBlockTest_Var1() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки CS-Cart
        csCartSettings.navigateToAppearanceSettings();
        if(csCartSettings.settingQuickView.isSelected()){
            csCartSettings.settingQuickView.click();
            csCartSettings.button_Save.click();
        }

        //Задаём настройки модуля
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции "Фен Valera"
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        //Берём ID данной промо-акции
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String [] split = currentUrl.split("id=");
        String promotionID = split[1];
        promotionSettings.clickAndType_field_DetailedDescription();
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        promotionSettings.tab_Conditions.scrollIntoView(false).click();
        promotionSettings.button_AddProductsToCondition.click();
        $(".ui-dialog-title").shouldBe(Condition.visible);
        promotionSettings.field_SearchProduct.click();
        promotionSettings.field_SearchProduct.sendKeys("Samsung серии 3 15.6\" 300V5A");
        promotionSettings.field_SearchProduct.sendKeys(Keys.ENTER);
        promotionSettings.checkProductToCondition.click();
        promotionSettings.button_AddAndClose.click();
        csCartSettings.button_Save.click();

        //Работаем с блоком "Мульти товар дня"
        MultiBlock multiBlock = csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_LightV2.click();
        csCartSettings.setLayoutAsDefault();
        csCartSettings.layout_TabHomePage.click();
        csCartSettings.switchOffBlock_DealOfTheDay();
        //Создаём блок "Мульти Товар дня"
        if (!$x("//div[@title=\"MultiBlock - AutoTest\"]").exists()) {
            multiBlock.addNewBlock();
            csCartSettings.popupWindow.shouldBe(Condition.enabled);
            multiBlock.tab_CreateNewBlock.click();
            multiBlock.multiBlock.click();
            $("#ui-id-2").shouldBe(Condition.enabled);
            multiBlock.blockName.click();
            multiBlock.blockName.sendKeys("MultiBlock - AutoTest");
            multiBlock.tab_Content.click();
            multiBlock.button_AddPromotionsToBlock.click();
            $("#ui-id-3").shouldBe(Condition.enabled);
            $(("input[id^='checkbox_id_" + promotionID)).click();
            multiBlock.button_AddAndCloseSelectedPromotions.click();
            multiBlock.button_CreateBlock.click();
        }
        //Задаём настройки блоку "Мульти Товар дня"
        multiBlock.blockProperties.click();
        csCartSettings.popupWindow.shouldBe(Condition.enabled);
        multiBlock.tab_BlockSettings.click();
        if (!multiBlock.setting_DoNotScrollAutomatically.isSelected()) {
            multiBlock.setting_DoNotScrollAutomatically.click();
        }
        multiBlock.setting_ItemQuantity.click();
        multiBlock.setting_ItemQuantity.setValue("5");
        if (multiBlock.setting_HideAddToCart.isSelected()) {
            multiBlock.setting_HideAddToCart.click();
        }
        if (!multiBlock.setting_DisplayPromotionCountdown.isSelected()) {
            multiBlock.setting_DisplayPromotionCountdown.click();
        }
        multiBlock.button_SaveBlockProperties.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfigurations_MultiBlockTest_Var1")
    public void check_Block(){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();

        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").hover();
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
        softAssert.assertTrue($$(".ab__deal_of_the_day .ty-list-price.ty-nowrap").isEmpty(),
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
        makePause();
        screenshot("800 MultiBlockTest_Var1 - Multi block");
        selectLanguage_RTL();
        stPromotions.block_DealOfTheDay.scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").hover();
        screenshot("805 MultiBlockTest_Var1 - Multi block (RTL)");
        softAssert.assertAll();
    }
}