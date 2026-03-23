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
        Utils.scrollToTabAndClick(promotionSettings.tab_Conditions);
        if ($$x("//label[contains(text(), 'Категории')]").isEmpty()) {
            $("a[id='sw_promotion_data[conditions][set]']").click();
            $("div[class='cm-popup-box btn-group open']").shouldBe(Condition.appear);
            $("a[title='любое']").click();
            promotionSettings.button_AddCondition.click();
            promotionSettings.selectCondition.selectOptionByValue("categories");
            promotionSettings.button_AddCategories.shouldBe(Condition.enabled).click();
            $(".ui-dialog-title").shouldBe(Condition.visible);
            if ($("span[title='Свернуть список'][style='display: none;']").exists()) {
                $x("//span[@id='c_company_1']/..//span[contains(@class, 'icon-caret-right')]").click();
                $("#category_166").click();
                $("#category_167").click();
                $("#input_cat_165").click(); //Планшеты

                $("#category_203").click();
                $("#category_215").click();
                $("#input_cat_217").click(); //Спальники
                $("#input_cat_218").click(); //Палатки

                $("#category_250").click();
                $("#input_cat_251").click(); //Калькуляторы
            } else {
                $("#input_cat_165").click(); //Планшеты
                $("#input_cat_217").click(); //Спальники
                $("#input_cat_218").click(); //Палатки
                $("#input_cat_251").click(); //Калькуляторы
                $("#input_cat_231").click(); //Джаз
            }
            $(".cm-form-dialog-closer").click();
        }

        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        Utils.scrollToTabAndClick(promotionSettings.tab_ABExtPromotions);
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
        basicPage.navigateToStorefront(1);

        StPromotions stPromotions = new StPromotions();
        AssertsPage assertsPage = new AssertsPage();

        //Проверяем, что отсутствует фильтр товаров, когда выбрано категорию "Все категории"
        assertsPage.assertElementPresence(assertsPage.filterByProducts, "on the promotion page 'All categories'!", false);

        //Проверяем, что присутствует счётчик на странице промо-акции
        assertsPage.assertElementPresence(assertsPage.countdown, "on the promotion page!", true);

        //Проверяем, что присутствует кнопка "Больше товаров из категории" -- настройка "Группировать по категории"
        assertsPage.assertElementPresence(assertsPage.button_MoreProductsFromCategory, "on the promotion page!", true);

        $(".ab-dotd-more-icon").scrollIntoCenter();
        screenshot("450 PromotionPage_Var3_GroupByCategory - Promotion page, Grid");
        $(".ab-dotd-more-products a[href$='cid=166']").hover().click();

        //Проверяем, что присутствует блок товаров на странице промо-акции, когда выбрано категорию "Электроника"
        assertsPage.assertElementPresence(assertsPage.productBlock, "on the promotion page 'Electronics'!", true);

        //Проверяем, что присутствует фильтр товаров, когда выбрано категорию "Электроника"
        assertsPage.assertElementPresence(assertsPage.filterByProducts, "on the promotion page 'Electronics'!", true);

        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_WithoutOptions);
        Utils.waitForSpinnerDisappear();
        stPromotions.productsOnPromotionPage.scrollIntoCenter();
        screenshot("455 PromotionPage_Var3_GroupByCategory - Promotion page, Without options");
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_CompactList);
        Utils.waitForSpinnerDisappear();
        stPromotions.productsOnPromotionPage.scrollIntoCenter();
        screenshot("460 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list");

        Utils.selectLanguage("ar");
        stPromotions.productsOnPromotionPage.scrollIntoCenter();
        screenshot("465 PromotionPage_Var3_GroupByCategory - Promotion page, Compact list (RTL)");
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_WithoutOptions);
        Utils.waitForSpinnerDisappear();
        stPromotions.productsOnPromotionPage.scrollIntoCenter();
        screenshot("470 PromotionPage_Var3_GroupByCategory - Promotion page, Without options (RTL)");
        Utils.scrollToTabAndClick(stPromotions.categoryTemplate_Grid);
        Utils.waitForSpinnerDisappear();
        screenshot("475 PromotionPage_Var3_GroupByCategory - Promotion page, Grid (RTL)");
    }
}