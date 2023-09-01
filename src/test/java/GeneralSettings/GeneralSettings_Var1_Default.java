package GeneralSettings;

import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;

/*
В данном тест-кейсе используются значения по умолчанию:
* Обратный отсчёт до -- Окончания дня
* Тип счётчика --       Javascript
* Максимальная высота описания --   250
* Промо-акций на страницу --        12
* Выделение промо-акции --          1
* Количество отображаемых промо-акций в списках товаров --  Не отображать
* Показ истекших промо-акций --     да
* Показ ожидаемых промо-акций --    да
*/

public class GeneralSettings_Var1_Default extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var1_Default(){
        //Задаём настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        /*csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.tab_Settings.click();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_day");
        addonSettings.setting_CountdownType.selectOptionByValue("javascript");
        addonSettings.clickAndType_setting_MaximumHeightOfDescription("250");
        addonSettings.clickAndType_setting_PromotionsPerPage("12");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("0");
        if(!addonSettings.setting_ShowExpiredPromotions.isSelected()){
            addonSettings.setting_ShowExpiredPromotions.click();
        }
        if(!addonSettings.setting_ShowAwaitingPromotions.isSelected()){
            addonSettings.setting_ShowAwaitingPromotions.click();
        }
        addonSettings.button_SaveSettings.click();*/

        //Работаем на странице редактирования промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        promotionSettings.clickAndType_field_DetailedDescription();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();
        }
        promotionSettings.setDateForSetting_AvailableFrom();
        promotionSettings.setDateForSetting_AvailableTill();
        csCartSettings.button_Save.click();
    }
}