import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import storefront.AssertsPage;
import testRunner.TestRunner;
import testRunner.Utils;

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
        BasicPage basicPage = new BasicPage();
        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        Utils.setCheckboxState(promotionSettings.setting_StopOtherRules, false);
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        Utils.scrollToTabAndClick(promotionSettings.tab_ABExtPromotions);
        Utils.setCheckboxState(promotionSettings.check_UseFilterByProducts, false);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, true);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, true);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnPromotionPage, false);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var2")
    public void check_PromotionPage_Var2() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        basicPage.navigateToStorefront(1);

        AssertsPage assertsPage = new AssertsPage();

        //Проверяем, что отсутствует фильтр товаров на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.filterByProducts, "on the promotion page!", false);

        //Проверяем, что отсутствует блок товаров на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.productBlock, "on the promotion page!", false);

        //Проверяем, что отсутствует счётчик на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.countdown, "on the promotion page!", false);

        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        assertsPage.assertPromotionPeriod_TillTheEndOfCurrentDay();

        screenshot("400 PromotionPage_Var2 - Promotion page");
        Utils.selectLanguage("ar");
        screenshot("405 PromotionPage_Var2 - Promotion page (RTL)");
    }
}