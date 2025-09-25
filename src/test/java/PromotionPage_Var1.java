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
- Тип счётчика --   FlipClock
- Количество отображаемых промо-акций в списках товаров --  2

Настройки на странице промо-акции:
* Задать период доступности --  да, для поля "Доступна до"
* Не применять другие промо-акции --    выкл
* Использовать фильтр по товарам --     да
* Скрыть блок товаров --        нет
* Отображать счётчик на странице промо-акции -- да
*/

public class PromotionPage_Var1 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionPage_Var1() {
        BasicPage basicPage = new BasicPage();
        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        Utils.setCheckboxState(promotionSettings.setting_StopOtherRules,false);
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        Utils.setCheckboxState(promotionSettings.check_UseFilterByProducts, true);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, false);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnPromotionPage, true);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var1")
    public void check_PromotionPage_Var1() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        basicPage.navigateToStorefront(1);

        AssertsPage assertsPage = new AssertsPage();

        //Проверяем, что присутствует фильтр товаров на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.filterByProducts, "on the promotion page!", true);

        //Проверяем, что присутствует блок товаров на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.productBlock, "on the promotion page!", true);

        //Проверяем, что присутствует счётчик на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.countdown, "on the promotion page!", true);

        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.flipClock, "on the promotion page!", true);

        screenshot("300 PromotionPage_Var1 - Promotion page");
        Utils.selectLanguage("ar");
        sleep(2000);
        screenshot("305 PromotionPage_Var1 - Promotion page (RTL)");
    }
}