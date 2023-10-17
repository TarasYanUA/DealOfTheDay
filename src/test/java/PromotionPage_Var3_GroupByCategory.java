import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import static com.codeborne.selenide.Selenide.*;

/*
Настройки на странице промо-акции:
    Вкладка "Условия"
- Категории: Ноутбуки, Планшеты, Спальники, Палатки, Калькуляторы, Джаз

    Вкладка "АВ: Расширенные промо-акции"
* Группировать по категории --  да
* Использовать фильтр по товарам --     да
* Скрыть блок товаров --        нет
* Отображать счётчик на странице промо-акции -- да
* Задать период доступности --  да, Доступен до
*/

public class PromotionPage_Var3_GroupByCategory extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_PromotionPage_Var3_GroupByCategory(){
        CsCartSettings csCartSettings = new CsCartSettings();
        //Задаём Условия промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();

        //Вкладка "Условия" у промо-акции
        promotionSettings.tab_Conditions.click();
        if($$x("//label[contains(text(), 'Категории')]").isEmpty() && $$(".cm-delete-row").size() >= 2){
            $(".cm-delete-row").hover().click();
            csCartSettings.button_Save.click();
        $(".cm-notification-close").shouldBe(Condition.enabled).click();
        $("a[id='sw_promotion_data[conditions][set]']").click();
        $("a[title='любое']").click();
        promotionSettings.button_AddCondition.click();
        promotionSettings.selectCondition.selectOptionByValue("categories");
        promotionSettings.button_AddCategories.shouldBe(Condition.enabled).click();
        $(".ui-dialog-title").shouldBe(Condition.visible);
        $("#input_cat_169").click(); //Ноутбуки
        $("#input_cat_165").click(); //Планшеты
        $("#input_cat_217").click(); //Спальники
        $("#input_cat_218").click(); //Палатки
        $("#input_cat_251").click(); //Калькуляторы
        $("#input_cat_231").click(); //Джаз
        $(".cm-form-dialog-closer").click();
        }

        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if(!promotionSettings.check_GroupByCategory.isSelected()){
            promotionSettings.check_GroupByCategory.click();
        }
        if(!promotionSettings.check_UseFilterByProducts.isSelected()){
            promotionSettings.check_UseFilterByProducts.click();
        }
        if (promotionSettings.check_HideProductBlock.isSelected()){
            promotionSettings.check_HideProductBlock.click();
        }
        if(!promotionSettings.check_DisplayCountdownOnPromotionPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnPromotionPage.click();
        }
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

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var3_GroupByCategory")
    public void check_PromotionPage_Var3_GroupByCategory() {
        CsCartSettings csCartSettings = new CsCartSettings();
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        csCartSettings.gearWheelOnTop.click();
        promotionSettings.button_PreviewPromotion.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();

        StPromotions stPromotions = new StPromotions();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что присутствует фильтр товаров на странице промо-акции
        softAssert.assertTrue(stPromotions.filterByProducts.exists(),
                "There is no product filters on the promotion page!");  //Ошибка https://abteam.planfix.com/task/42437
        //Проверяем, что присутствует блок товаров на странице промо-акции
        softAssert.assertTrue(stPromotions.productBlock.exists(),
                "There is no product block on the promotion page!");
        //Проверяем, что присутствует счётчик на странице промо-акции
        softAssert.assertTrue(stPromotions.countdown.exists(),
                "There is no countdown on the promotion page!");
        //Проверяем, что присутствует кнопка "Больше товаров из категории" -- настройка "Группировать по категории"
        softAssert.assertTrue(!stPromotions.button_MoreProductsFromCategory.isEmpty(),
                "There is no any button 'More products from category' on the promotion page!");
        screenshot("450 PromotionPage_Var3_GroupByCategory - Promotion page, Grid");
        $(".ab-dotd-categories-filter a[href$='cid=166']").click();
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();
        screenshot("455 PromotionPage_Var3_GroupByCategory - Promotion page, Without options");
        stPromotions.categoryTemplate_CompactList.click();
        makePause();
        screenshot("460 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list");

        selectLanguage_RTL();
        makePause();
        screenshot("465 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list (RTL)");
        stPromotions.categoryTemplate_WithoutOptions.click();
        makePause();
        screenshot("470 PromotionPage_Var3_GroupByCategory - Promotion page, Without options (RTL)");
        stPromotions.categoryTemplate_Grid.click();
        makePause();
        screenshot("475 PromotionPage_Var3_GroupByCategory - Promotion page, Grid (RTL)");
        softAssert.assertAll();
    }
}