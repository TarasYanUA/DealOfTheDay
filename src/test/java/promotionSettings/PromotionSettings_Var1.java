package promotionSettings;

import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import generalSettings.TestRunner;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;

public class PromotionSettings_Var1 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionSettings_Var1(){
        //Задаём настройки промо-акции
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()){  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click();
        }
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click();
        }
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(!promotionSettings.check_UseFilterByProducts.isSelected()){
            promotionSettings.check_UseFilterByProducts.click();
        }
        if (promotionSettings.check_HideProductBlock.isSelected()){
            promotionSettings.check_HideProductBlock.click();
        }
        if(!promotionSettings.check_DisplayLabelInProductLists.isSelected()){
            promotionSettings.check_DisplayLabelInProductLists.click();
        }
        if(!promotionSettings.check_DisplayPromotionInProductLists.isSelected()){
            promotionSettings.check_DisplayPromotionInProductLists.click();
        }
        if(!promotionSettings.check_DisplayCountdownOnProductPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnProductPage.click();
        }
        if(!promotionSettings.check_DisplayCountdownOnPromotionPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnPromotionPage.click();
        }
        csCartSettings.button_Save.click();

        //Настройки модуля ещё, которые должны соответствовать

    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionSettings_Var1")
    public void check_PromotionSettings_Var1() {
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.promotion_RacingCard.click();
        csCartSettings.gearWheelOnTop.click();
        promotionSettings.button_PreviewPromotion.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
    }
}
