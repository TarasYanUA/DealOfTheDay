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
    public void setConfiguration_PromotionPage_Var3_GroupByCategory() {
        BasicPage basicPage = new BasicPage();
        //Задаём Условия промо-акции "Купите фотоаппарат"
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        //Устанавливаем сегодняшнюю дату для поля "Доступна до"
        Utils.clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();

        //Вкладка "Условия" у промо-акции
        promotionSettings.tab_Conditions.scrollIntoView(false).click();
        if ($$x("//label[contains(text(), 'Категории')]").isEmpty()) {
            $("a[id='sw_promotion_data[conditions][set]']").click();
            $("div[class='cm-popup-box btn-group open']").shouldBe(Condition.appear);
            $("a[title='любое']").click();
            promotionSettings.button_AddCondition.click();
            promotionSettings.selectCondition.selectOptionByValue("categories");
            promotionSettings.button_AddCategories.shouldBe(Condition.enabled).click();
            $(".ui-dialog-title").shouldBe(Condition.visible);
            $("#input_cat_165").click(); //Планшеты
            $("#input_cat_217").click(); //Спальники
            $("#input_cat_218").click(); //Палатки
            $("#input_cat_251").click(); //Калькуляторы
            $("#input_cat_231").click(); //Джаз
            $(".cm-form-dialog-closer").click();
        }

        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        Utils.setCheckboxState(promotionSettings.check_GroupByCategory, true);
        Utils.setCheckboxState(promotionSettings.check_UseFilterByProducts, true);
        Utils.setCheckboxState(promotionSettings.check_HideProductBlock, false);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnPromotionPage, true);
        basicPage.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_PromotionPage_Var3_GroupByCategory")
    public void check_PromotionPage_Var3_GroupByCategory() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        basicPage.saveAndGoToStorefront_ProductPage(1);

        StPromotions stPromotions = new StPromotions();
        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что отсутствует фильтр товаров, когда выбрано категорию "Все категории"
        softAssert.assertFalse(stPromotions.filterByProducts.exists(),
                "There is the product filters on the category 'All categories' but shouldn't on the promotion page 'All categories'!");

        //Проверяем, что присутствует счётчик на странице промо-акции
        softAssert.assertTrue(stPromotions.countdown.exists(),
                "There is no countdown on the promotion page!");

        //Проверяем, что присутствует кнопка "Больше товаров из категории" -- настройка "Группировать по категории"
        softAssert.assertTrue(!stPromotions.button_MoreProductsFromCategory.isEmpty(),
                "There is no any button 'More products from category' on the promotion page!");

        $(".ab-dotd-more-icon").scrollIntoCenter();
        screenshot("450 PromotionPage_Var3_GroupByCategory - Promotion page, Grid");
        $(".ab-dotd-categories-filter a[href$='cid=166']").hover().click();

        //Проверяем, что присутствует блок товаров на странице промо-акции, когда выбрано категорию "Электроника"
        softAssert.assertTrue(stPromotions.productBlock.exists(),
                "There is no product block on the promotion page!");

        //Проверяем, что присутствует фильтр товаров, когда выбрано категорию "Электроника"
        softAssert.assertTrue(stPromotions.filterByProducts.exists(),
                "There is no product filters on the promotion page 'Electronics'!");

        stPromotions.categoryTemplate_WithoutOptions.scrollIntoCenter().click();
        stPromotions.productsOnPromotionPage.scrollIntoView(true);
        sleep(2000);
        screenshot("455 PromotionPage_Var3_GroupByCategory - Promotion page, Without options");
        stPromotions.categoryTemplate_CompactList.scrollIntoCenter().click();
        stPromotions.productsOnPromotionPage.scrollIntoView(true);
        sleep(2000);
        screenshot("460 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list");

        Utils.selectLanguage("ar");
        stPromotions.productsOnPromotionPage.scrollIntoView(true);
        sleep(2000);
        screenshot("465 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list (RTL)");
        stPromotions.categoryTemplate_WithoutOptions.scrollIntoCenter().click();
        stPromotions.productsOnPromotionPage.scrollIntoView(true);
        sleep(2000);
        screenshot("470 PromotionPage_Var3_GroupByCategory - Promotion page, Without options (RTL)");
        stPromotions.categoryTemplate_Grid.scrollIntoCenter().click();
        sleep(2000);
        screenshot("475 PromotionPage_Var3_GroupByCategory - Promotion page, Grid (RTL)");
        softAssert.assertAll();
    }
}