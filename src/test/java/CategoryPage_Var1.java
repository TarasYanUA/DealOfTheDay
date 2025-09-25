import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import storefront.AssertsPage;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Настройки модуля:
- Выделение промо-акции --  1
- Количество отображаемых промо-акций в списках товаров --  1

Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Скрыть блок товаров --        нет
* Отображать лейбл (стикер) "Акция" в списках товаров --    да
* Отображать промо-акцию в списках товаров --   да
*/

public class CategoryPage_Var1 extends TestRunner {
    @Test(priority = 1)
    public void set_CategoryPage_Var1(){
        BasicPage basicPage = new BasicPage();
        //Задаём настройки CS-Cart
        basicPage.navigateTo_AppearanceSettingsAndQuickViewOn();

        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("1");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, false); //убираем период доступности, чтобы промо-акция всегда отображалась
        Utils.setCheckboxState(promotionSettings.setting_StopOtherRules, false);
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        Utils.scrollToTabAndClick(promotionSettings.tab_ABExtPromotions);
        Utils.setCheckboxState(promotionSettings.check_DisplayLabelInProductLists, true);
        Utils.setCheckboxState(promotionSettings.check_DisplayPromotionInProductLists, true);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "set_CategoryPage_Var1")
    public void check_CategoryPage(){
        //Переходим на страницу категории
        BasicPage basicPage = new BasicPage();
        basicPage.navigateTo_CategoryPage();
        basicPage.category_Notebooks.click();
        basicPage.navigateToStorefront(1);

        StPromotions stPromotions = new StPromotions();
        AssertsPage assertsPage = new AssertsPage();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Сетка"
        assertsPage.assertElementPresence(assertsPage.labelOnCategoryPage, "on the category page 'Grid'!", true);

        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Сетка"
        assertsPage.assertElementPresence(assertsPage.promotionOnCategoryPage, "on the category page 'Grid'!", true);

        stPromotions.chooseAnyProduct.hover();
        screenshot("600 CategoryPage_Var1 - Template Grid");
        stPromotions.button_QuickView.hover().click();
        $(".ui-dialog-titlebar").shouldBe(Condition.visible);

        //Проверяем, что заголовок промо-акции присутствует в окне Быстрого просмотра
        assertsPage.assertElementPresence(assertsPage.blockTitle, "in the quick view window!", true);

        //Проверяем, что присутствует шапка промо-акции в окне Быстрого просмотра
        assertsPage.assertElementPresence(assertsPage.promotionHeaderInQuickView, "",true);

        stPromotions.button_ClosePopupWindow.hover();
        screenshot("605 CategoryPage_Var1 - Quick view");
        stPromotions.button_ClosePopupWindow.click();
        Utils.selectLanguage("ar");
        stPromotions.chooseAnyProduct.hover();
        screenshot("610 CategoryPage_Var1 - Template Grid (RTL)");
        stPromotions.button_QuickView.hover().click();
        $(".ui-dialog-titlebar").shouldBe(Condition.visible);
        stPromotions.button_ClosePopupWindow.hover();
        screenshot("615 CategoryPage_Var1 - Quick view (RTL)");

        //Переключаем шаблоны страницы категории
        stPromotions.button_ClosePopupWindow.click();
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_WithoutOptions);
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Список без опций"
        assertsPage.assertElementPresence(assertsPage.labelOnCategoryPage, "on the category page 'Without options'!", true);

        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Список без опций"
        assertsPage.assertElementPresence(assertsPage.promotionOnCategoryPage, "on the category page 'Without options'!", true);

        screenshot("620 CategoryPage_Var1 - Template Without options (RTL)");
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_CompactList);
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Компактный список"
        assertsPage.assertElementPresence(assertsPage.labelOnCategoryPage, "on the category page 'Compact list'!", true);

        screenshot("625 CategoryPage_Var1 - Template Compact list (RTL)");
        Utils.selectLanguage("ru");
        screenshot("630 CategoryPage_Var1 - Template Compact list");
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_WithoutOptions);
        Utils.waitForSpinnerDisappear();
        screenshot("635 CategoryPage_Var1 - Template Without options");
    }
}