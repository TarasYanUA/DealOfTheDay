import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

/*
Настройки модуля:
- Количество отображаемых промо-акций в списках товаров --  2

Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Отображать лейбл (стикер) "Акция" в списках товаров --    нет
* Отображать промо-акцию в списках товаров --   да
*/

public class CategoryPage_Var2 extends TestRunner {
    @Test(priority = 1)
    public void set_CategoryPage_Var2(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём настройки CS-Cart
        csCartSettings.navigateTo_AppearanceSettings();
        if(!csCartSettings.setting_QuickView.isSelected()){
            csCartSettings.setting_QuickView.click();
            csCartSettings.button_Save.click();
        }

        //Задаём настройки модуля
        AddonSettings addonSettings = csCartSettings.navigateTo_AddonSettings();
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        addonSettings.button_SaveSettings.click();

        //Задаём настройки промо-акции "Гоночный картинг"
        PromotionSettings promotionSettings = csCartSettings.navigateTo_PromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()){  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click(); }
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click(); }
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(promotionSettings.check_DisplayLabelInProductLists.isSelected()){
            promotionSettings.check_DisplayLabelInProductLists.click(); }
        if(!promotionSettings.check_DisplayPromotionInProductLists.isSelected()){
            promotionSettings.check_DisplayPromotionInProductLists.click(); }
        csCartSettings.button_Save.click();

        //Задаём настройки промо-акции "фен VALERA"
        csCartSettings.navigateTo_PromotionSettings();
        promotionSettings.promotion_BuyHairDryerVALERA.click();
        if(promotionSettings.setting_UseAvailablePeriod.isSelected()){  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click(); }
        if(promotionSettings.setting_StopOtherRules.isSelected()){
            promotionSettings.setting_StopOtherRules.click(); }
        //Вкладка "Условия"
        promotionSettings.tab_Conditions.scrollIntoView(false).click();
        promotionSettings.button_AddProductsToCondition.click();
        $(".ui-dialog-title").shouldBe(Condition.visible);
        promotionSettings.field_SearchProduct.click();
        promotionSettings.field_SearchProduct.sendKeys("M0219A3GX3");
        promotionSettings.field_SearchProduct.sendKeys(Keys.ENTER);
        promotionSettings.checkProductToCondition.click();
        promotionSettings.button_AddAndClose.click();
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(promotionSettings.check_DisplayLabelInProductLists.isSelected()){
            promotionSettings.check_DisplayLabelInProductLists.click(); }
        if(!promotionSettings.check_DisplayPromotionInProductLists.isSelected()){
            promotionSettings.check_DisplayPromotionInProductLists.click(); }
        csCartSettings.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "set_CategoryPage_Var2")
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
        //Проверяем, что отсутствует лейбл на странице категории с шаблоном "Сетка"
        softAssert.assertFalse(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is a promotion label on the category page 'Grid' but shouldn't!");
        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Сетка"
        softAssert.assertTrue(!stPromotions.promotionOnCategoryPage.isEmpty(),
                "There is no any promotion on the category page 'Grid'!");
        //Проверяем, что у одного товара присутствует сразу две промо-акции
        softAssert.assertTrue($$("form[name=\"product_form_219\"] .ab-dotd-category-promo").size() ==2,
                "There are no two promotions in one product on the category page, Grid template!");
        $("#det_img_219desktop").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        $("form[name='product_form_219'] .ut2-gl__image").hover();
        screenshot("700 CategoryPage_Var2 - Template Grid");
        stPromotions.button_QuickView.hover().click();
        $(".ui-dialog-titlebar").shouldBe(Condition.visible);
        //Проверяем, что присутствует шапка промо-акции в окне Быстрого просмотра
        softAssert.assertTrue(stPromotions.promotionHeaderInQuickView.exists(),
                "There is no promotion header in the quick view window!");
        makePause();
        stPromotions.button_ClosePopupWindow.hover();
        screenshot("705 CategoryPage_Var2 - Quick view");
        stPromotions.button_ClosePopupWindow.click();
        selectLanguage_RTL();
        $("#det_img_219desktop").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        $("form[name='product_form_219'] .ut2-gl__image").hover();
        screenshot("710 CategoryPage_Var2 - Template Grid (RTL)");
        stPromotions.button_QuickView.hover().click();
        makePause();
        stPromotions.button_ClosePopupWindow.hover();
        screenshot("715 CategoryPage_Var2 - Quick view (RTL)");

        //Переключаем шаблоны страницы категории
        stPromotions.button_ClosePopupWindow.click();
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();
        //Проверяем, что промо-акция отображается на странице категории с шаблоном "Список без опций"
        softAssert.assertTrue(!stPromotions.promotionOnCategoryPage.isEmpty(),
                "There is no any promotion on the category page 'Without options'!");
        //Проверяем, что у одного товара присутствует сразу две промо-акции, "Список без опций"
        softAssert.assertTrue($$("form[name=\"product_form_219\"] .ab-dotd-category-promo").size() ==2,
                "There are no two promotions in one product on the category page, 'List without options' template!");
        //Проверяем, что отсутствует лейбл на странице категории с шаблоном "Список без опций"
        softAssert.assertFalse(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is a promotion label on the category page 'Without options' but shouldn't!");
        screenshot("720 CategoryPage_Var2 - Template Without options (RTL)");
        stPromotions.categoryTemplate_CompactList.click();
        //Проверяем, что отсутствует лейбл на странице категории с шаблоном "Компактный список"
        softAssert.assertFalse(!stPromotions.labelOnCategoryPage.isEmpty(),
                "There is a promotion label on the category page 'Compact list' but shouldn't!");
        makePause();
        screenshot("725 CategoryPage_Var2 - Template Compact list (RTL)");
        selectLanguage_RU();
        makePause();
        screenshot("730 CategoryPage_Var2 - Template Compact list");
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();
        screenshot("735 CategoryPage_Var2 - Template Without options");
        softAssert.assertAll();
    }
}