import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import generalSettings.TestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

/*
Настройки модуля:
- Количество отображаемых промо-акций в списках товаров --  2
- Выделение промо-акции --  1

Настройки на странице промо-акции:
* Задать период доступности --  да
* Не применять другие промо-акции --    выкл
* Использовать фильтр по товарам --     нет
* Скрыть блок товаров --        да
* Отображать счётчик на странице промо-акции -- нет
*/

public class PromotionPage_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionPage_Var2(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки модуля
        csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click(); }
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.scrollIntoView(false).click();
        if(promotionSettings.check_UseFilterByProducts.isSelected()){
            promotionSettings.check_UseFilterByProducts.click(); }
        if(!promotionSettings.check_HideProductBlock.isSelected()){
            promotionSettings.check_HideProductBlock.click(); }
        if (!promotionSettings.check_HideProductBlock.isSelected()){
            promotionSettings.check_HideProductBlock.click(); }
        if(promotionSettings.check_DisplayCountdownOnPromotionPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnPromotionPage.click(); }
        csCartSettings.button_Save.click();
    }
    public void clearBothFieldsAvailable(){
        PromotionSettings promotionSettings = new PromotionSettings();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.setting_AvailableFrom.clear();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.setting_AvailableTill.clear();
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var2")
    public void check_PromotionPage_Var2() {
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.promotion_RacingCard.click();
        csCartSettings.gearWheelOnTop.click();
        promotionSettings.button_PreviewPromotion.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        //Проверяем, что отсутствует фильтр товаров на странице промо-акции
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse($x("//div[contains(text(), 'Фильтры товаров')]").exists(),
                "There are the product filters on the promotion page but shouldn't!");
        //Проверяем, что отсутствует блок товаров на странице промо-акции
        softAssert.assertFalse($("#promotions_view_pagination_contents").exists(),
                "There is a product block on the promotion page but shouldn't!");
        //Проверяем, что отсутствует счётчик на странице промо-акции
        softAssert.assertFalse($(".ab__dotd_promotion-timer").exists(),
                "There is a countdown on the promotion page but shouldn't!");
        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(":");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate,
                "Promotion period is not till the end of the current day!");
        screenshot("400 PromotionPage_Var2 - Promotion page");
        softAssert.assertAll();
    }
}