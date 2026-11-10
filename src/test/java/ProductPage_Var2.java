import adminPanel.AddonSettings;
import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.ElementsCollection;
import org.testng.annotations.Test;
import storefront.AssertsPage;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

/*
Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Тип отображения на странице товара -- Несколько акций
*/

public class ProductPage_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_ProductPage() {
        BasicPage basicPage = new BasicPage();
        //Задаём настройки промо-акции
        PromotionSettings promotionSettings = basicPage.navigateTo_PromotionSettings();
        basicPage.chooseRussianLanguage();
        promotionSettings.promotion_RacingCard.click();
        Utils.setCheckboxState(promotionSettings.setting_UseAvailablePeriod, false); //убираем период доступности, чтобы промо-акция всегда отображалась
        Utils.setCheckboxState(promotionSettings.setting_StopOtherRules, false);
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        Utils.scrollToTabAndClick(promotionSettings.tab_ABExtPromotions);
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnProductPage, false);

        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_DisplayTypeOnProductPage.selectOptionByValue("multiple");
        addonSettings.button_SaveSettings.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_ProductPage")
    public void check_ProductPage() {
        BasicPage basicPage = new BasicPage();
        String productCode = "M0219A3GX3";
        basicPage.field_SearchOnTop.setValue(productCode).pressEnter();
        basicPage.productTemplate.selectOptionByValue("default_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(1);
        Utils.selectLanguage("ru");

        AssertsPage assertsPage = new AssertsPage();

        //Проверяем, что тип отображения промо-акций на странице товара -- Несколько акций
        assertsPage.assertElementPresence(assertsPage.typeOnProductPage_Multiple, "", true);

        sleep(2000);
        screenshot("600 ProductPage - Product page, Default");
        screenshotPromotionPopUp("601 ProductPage - Promotion pop-up - Multiple");

        Utils.selectLanguage("ar");
        screenshot("602 ProductPage - Product page, Default (RTL)");
        Utils.shiftBrowserTab(0);
        basicPage.field_SearchOnTop.setValue(productCode).pressEnter();
        basicPage.productTemplate.selectOptionByValue("bigpicture_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(2);
        screenshot("604 ProductPage - Product page, BigPicture");
        Utils.selectLanguage("ar");
        screenshot("606 ProductPage - Product page, BigPicture (RTL)");
        basicPage.selectProductTemplate("abt__ut2_bigpicture_flat_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(3);
        screenshot("608 ProductPage - Product page, BigPictureFlat");
        Utils.selectLanguage("ar");
        screenshot("610 ProductPage - Product page, BigPictureFlat (RTL)");
        basicPage.selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(4);
        $(".ab_dotd_product_promotion_list").scrollIntoCenter();
        screenshot("612 ProductPage - Product page, Gallery");
        Utils.selectLanguage("ar");
        $(".ab_dotd_product_promotion_list").scrollIntoCenter();
        screenshot("614 ProductPage - Product page, Gallery (RTL)");
        basicPage.selectProductTemplate("abt__ut2_cascade_gallery_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(5);
        screenshot("616 ProductPage - Product page, CascadeGallery");
        Utils.selectLanguage("ar");
        screenshot("618 ProductPage - Product page, CascadeGallery (RTL)");
        basicPage.selectProductTemplate("abt__ut2_three_columns_template");
        basicPage.savePage();
        basicPage.navigateToStorefront(6);
        screenshot("620 ProductPage - Product page, Three-columned");
        Utils.selectLanguage("ar");
        screenshot("622 ProductPage - Product page, Three-columned (RTL)");
    }

    public void screenshotPromotionPopUp(String screenshotName) {
        ElementsCollection promotions = $$(".ab_dotd_product_promotion_list .ab_dotd_product_promotion_wrapper");
        promotions.shouldHave(sizeGreaterThan(0)).first().click();
        sleep(3000);
        screenshot(screenshotName);
        StPromotions stPromotions = new StPromotions();
        stPromotions.button_ClosePopupWindow.click();
    }
}