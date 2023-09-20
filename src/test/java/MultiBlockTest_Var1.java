import adminPanel.CsCartSettings;
import adminPanel.MultiBlock;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
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

        if (!$x("//div[contains(text(), 'My MultiBlockTest')]").exists()) {
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
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.hover();
        screenshot("800 MultiBlockTest_Var1 - Multi block");
    }
}