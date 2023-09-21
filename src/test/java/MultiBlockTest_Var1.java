import adminPanel.CsCartSettings;
import adminPanel.MultiBlock;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

public class MultiBlockTest_Var1 extends TestRunner {
    @Test(priority = 1)
    public void setConfigurations_MultiBlockTest_Var1() {
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки CS-Cart
        csCartSettings.navigateToAppearanceSettings();
        if(!csCartSettings.settingQuickView.isSelected()){
            csCartSettings.settingQuickView.click();
            csCartSettings.button_Save.click();
        }

        //Создаём блок "Мульти товар дня" и задаём настройки блока
        MultiBlock multiBlock = csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_LightV2.click();
        csCartSettings.setLayoutAsDefault();
        csCartSettings.layout_TabHomePage.click();

        if (!$x("//div[@title=\"My MultiBlockTest\"]").exists()) {
            multiBlock.addNewBlock();
            csCartSettings.popupWindow.shouldBe(Condition.enabled);
            multiBlock.tab_CreateNewBlock.click();
            multiBlock.multiBlock.click();
            $("#ui-id-2").shouldBe(Condition.enabled);
            multiBlock.blockName.click();
            multiBlock.blockName.sendKeys("My MultiBlockTest");
            multiBlock.tab_Content.click();
            multiBlock.button_AddPromotionsToBlock.click();
            $("#ui-id-3").shouldBe(Condition.enabled);
            multiBlock.chooseThreePromotionsForBlock();
            if ($(".object-container .cm-notification-close").exists()) {
                $(".object-container .cm-notification-close").click();
            }
            multiBlock.tab_BlockSettings.click();
            if (!multiBlock.setting_ShowPrice.isSelected()) {
                multiBlock.setting_ShowPrice.click();
            }
            if (!multiBlock.setting_EnableQuickView.isSelected()) {
                multiBlock.setting_EnableQuickView.click();
            }
            multiBlock.setting_ItemQuantity.click();
            multiBlock.setting_ItemQuantity.setValue("6");
            if (multiBlock.setting_HideAddToCart.isSelected()) {
                multiBlock.setting_HideAddToCart.click();
            }
            if (!multiBlock.setting_DisplayPromotionCountdown.isSelected()) {
                multiBlock.setting_DisplayPromotionCountdown.click();
            }
            multiBlock.button_CreateBlock.click();
        }
    }

    @Test(priority = 2, dependsOnMethods = "setConfigurations_MultiBlockTest_Var1")
    public void check_Block(){
        CsCartSettings csCartSettings = new CsCartSettings();
        MultiBlock multiBlock = csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_LightV2.click();
        csCartSettings.layout_TabHomePage.click();
        String text = $x("//div[@title=\"My MultiBlockTest\"]//small[@data-ca-block-manager=\"block_id\"]").getText();
        String[] split = text.split("#");
        String blockID = "ab__deal_of_the_day_" + split[1];
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        $(By.id(blockID)).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}").hover();

        StPromotions stPromotions = new StPromotions();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue($(".pd-promotion__title").exists(),
                "There is no title of the promotion in the multi block!");
        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue($(".promotion-descr").exists(),
                "There is no description of the promotion in the multi block!");
        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the multi block!");
        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the multi block!");
        //Проверяем, что в блоке присутствует цена
        softAssert.assertTrue($$(".ab__deal_of_the_day .ut2-gl__price").size() > 1,
                "There is no price at products in the multi block!");
        //Проверяем, что в блоке присутствует кнопка быстрого просмотра
        softAssert.assertTrue($$(".ab__deal_of_the_day a[data-ca-target-id=\"product_quick_view\"]").size() > 1,
                "There is no quick view button at the products in the multi block!");
        //Проверяем, что в блоке присутствует кнопка "Купить"
        softAssert.assertTrue($$(".ab__deal_of_the_day .ut2-icon-use_icon_cart").size() > 1,
                "There is no button 'Add to cart' at the products in the multi block!");
        //Проверяем, что в блоке присутствует счётчик
        softAssert.assertTrue($(".flipclock").exists(),
                "There is no countdown in the multi block!");
        makePause();
        screenshot("800 MultiBlockTest_Var1 - Multi block");
    }
}