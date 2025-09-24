import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
        promotionSettings.tab_ABExtPromotions.scrollIntoCenter().click();
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
        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Сетка"
        softAssert.assertTrue(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is no any promotion label on the category page 'Grid'!");

        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Сетка"
        softAssert.assertTrue(!stPromotions.promotionOnCategoryPage.isEmpty(),
                "There is no any promotion on the category page 'Grid'!");

        stPromotions.chooseAnyProduct.hover();
        screenshot("600 CategoryPage_Var1 - Template Grid");
        stPromotions.button_QuickView.hover().click();
        $(".ui-dialog-titlebar").shouldBe(Condition.visible);

        //Проверяем, что присутствует шапка промо-акции в окне Быстрого просмотра
        softAssert.assertTrue(stPromotions.promotionHeaderInQuickView.exists(),
                "There is no promotion header in the quick view window!");

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
        stPromotions.categoryTemplate_WithoutOptions.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Список без опций"
        softAssert.assertTrue(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is no any promotion label on the category page 'Without options'!");

        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Список без опций"
        softAssert.assertTrue(!stPromotions.promotionOnCategoryPage.isEmpty(),
                "There is no any promotion on the category page 'Without options'!");

        screenshot("620 CategoryPage_Var1 - Template Without options (RTL)");
        stPromotions.categoryTemplate_CompactList.click();
        Utils.waitForSpinnerDisappear();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Компактный список"
        softAssert.assertTrue(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is no any promotion label on the category page 'Compact list'!");

        screenshot("625 CategoryPage_Var1 - Template Compact list (RTL)");
        Utils.selectLanguage("ru");
        screenshot("630 CategoryPage_Var1 - Template Compact list");
        stPromotions.categoryTemplate_WithoutOptions.click();
        Utils.waitForSpinnerDisappear();
        screenshot("635 CategoryPage_Var1 - Template Without options");
        softAssert.assertAll();
    }
}