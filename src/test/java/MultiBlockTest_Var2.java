import adminPanel.CsCartSettings;
import adminPanel.MultiBlock;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

/*
Работаем с макетом Light v2 и блоком "Мульти товар дня"!
Настройки промо-акции "Фен Valera":
* Задать период доступности --  выкл

Настройки блока:
* Показывать цену --                нет
* Включить быстрый просмотр --      нет
* Количество элементов --           4
* Спрятать кнопку добавления товара в корзину --    да
* Отображать счётчик промо-акции -- нет
*/

public class MultiBlockTest_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfigurations_MultiBlockTest_Var2() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки CS-Cart
        csCartSettings.navigateToAppearanceSettings();
        if(!csCartSettings.settingQuickView.isSelected()){
            csCartSettings.settingQuickView.click();
            csCartSettings.button_Save.click();
        }

        //Задаём настройки промо-акции "Фен Valera"
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        //Берём ID данной промо-акции
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        String [] split = currentUrl.split("id=");
        String promotionID = split[1];
        promotionSettings.clickAndType_field_DetailedDescription();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        csCartSettings.button_Save.click();

        //Создаём блок "Мульти товар дня" и задаём настройки блока
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
        if (multiBlock.setting_ShowPrice.isSelected()) {
            multiBlock.setting_ShowPrice.click();
        }
        if (multiBlock.setting_EnableQuickView.isSelected()) {
            multiBlock.setting_EnableQuickView.click();
        }
        if (!multiBlock.setting_DoNotScrollAutomatically.isSelected()) {
            multiBlock.setting_DoNotScrollAutomatically.click();
        }
        multiBlock.setting_ItemQuantity.click();
        multiBlock.setting_ItemQuantity.setValue("4");
        if (!multiBlock.setting_HideAddToCart.isSelected()) {
            multiBlock.setting_HideAddToCart.click();
        }
        if (multiBlock.setting_DisplayPromotionCountdown.isSelected()) {
            multiBlock.setting_DisplayPromotionCountdown.click();
        }
        multiBlock.button_SaveBlockProperties.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfigurations_MultiBlockTest_Var2")
    public void check_Block(){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").hover();
        StPromotions stPromotions = new StPromotions();
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
        //Проверяем, что в блоке отсутствует цена
        softAssert.assertFalse(!$$(".ab__deal_of_the_day .ty-list-price.ty-nowrap").isEmpty(),
                "There is a price at products in the multi block but shouldn't!");
        //Проверяем, что в блоке отсутствует кнопка быстрого просмотра
        softAssert.assertFalse(!$$(".ab__deal_of_the_day a[data-ca-target-id=\"product_quick_view\"]").isEmpty(),
                "There is a quick view button at the products in the multi block but shouldn't!");
        //Проверяем, что в блоке отсутствует кнопка "Купить"
        softAssert.assertTrue($$(".ab__deal_of_the_day .ut2-icon-use_icon_cart").isEmpty(),
                "There is a button 'Add to cart' at the products in the multi block but shouldn't!");
        makePause();
        screenshot("900 MultiBlockTest_Var2 - Multi block");
        selectLanguage_RTL();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").hover();
        screenshot("905 MultiBlockTest_Var2 - Multi block (RTL)");
        softAssert.assertAll();
    }
}