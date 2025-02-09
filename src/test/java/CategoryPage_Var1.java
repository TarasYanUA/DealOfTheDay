import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;

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
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки CS-Cart
        csCartSettings.navigateTo_AppearanceSettings();
        if(!csCartSettings.setting_QuickView.isSelected()){
            csCartSettings.setting_QuickView.click();
            csCartSettings.button_Save.click();
        }

        //Задаём настройки модуля
        AddonSettings addonSettings = csCartSettings.navigateTo_AddonSettings();
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("1");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateTo_PromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()){  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click(); }
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click(); }
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(!promotionSettings.check_DisplayLabelInProductLists.isSelected()){
            promotionSettings.check_DisplayLabelInProductLists.click(); }
        if(!promotionSettings.check_DisplayPromotionInProductLists.isSelected()){
            promotionSettings.check_DisplayPromotionInProductLists.click(); }
        csCartSettings.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "set_CategoryPage_Var1")
    public void check_CategoryPage(){
        //Переходим на страницу категории
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateTo_CategoryPage();
        csCartSettings.category_Notebooks.click();
        makePause();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();

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

        makePause();
        stPromotions.button_ClosePopupWindow.hover();
        screenshot("605 CategoryPage_Var1 - Quick view");
        stPromotions.button_ClosePopupWindow.click();
        selectLanguage_RTL();
        stPromotions.chooseAnyProduct.hover();
        screenshot("610 CategoryPage_Var1 - Template Grid (RTL)");
        stPromotions.button_QuickView.hover().click();
        makePause();
        stPromotions.button_ClosePopupWindow.hover();
        screenshot("615 CategoryPage_Var1 - Quick view (RTL)");

        //Переключаем шаблоны страницы категории
        stPromotions.button_ClosePopupWindow.click();
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Список без опций"
        softAssert.assertTrue(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is no any promotion label on the category page 'Without options'!");

        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Список без опций"
        softAssert.assertTrue(!stPromotions.promotionOnCategoryPage.isEmpty(),
                "There is no any promotion on the category page 'Without options'!");

        screenshot("620 CategoryPage_Var1 - Template Without options (RTL)");
        stPromotions.categoryTemplate_CompactList.click();
        makePause();

        //Проверяем, что присутствует лейбл на странице категории с шаблоном "Компактный список"
        softAssert.assertTrue(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is no any promotion label on the category page 'Compact list'!");

        screenshot("625 CategoryPage_Var1 - Template Compact list (RTL)");
        selectLanguage_RU();
        makePause();
        screenshot("630 CategoryPage_Var1 - Template Compact list");
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();
        screenshot("635 CategoryPage_Var1 - Template Without options");
        softAssert.assertAll();
    }
}