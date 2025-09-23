import adminPanel.AddonSettings;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import adminPanel.BasicPage;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;

/*
Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Отображать счётчик на странице товара --  да
*/

public class ProductPage extends TestRunner {
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
        promotionSettings.tab_ABExtPromotions.click();
        Utils.setCheckboxState(promotionSettings.check_DisplayCountdownOnProductPage, false);

        //Задаём настройки модуля
        AddonSettings addonSettings = basicPage.navigateTo_AddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.button_SaveSettings.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_ProductPage")
    public void check_ProductPage() {
        BasicPage basicPage = new BasicPage();
        String productCode = "M0219A3GX3";
        basicPage.field_SearchOnTop.click();
        basicPage.field_SearchOnTop.setValue(productCode).pressEnter();
        basicPage.productTemplate.selectOptionByValue("default_template");
        basicPage.saveAndGoToStorefront_ProductPage(1);
        Utils.selectLanguage("ru");
        StPromotions stPromotions = new StPromotions();

        SoftAssert softAssert = new SoftAssert();

        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue(stPromotions.promotionHeader.exists(),
                "There is no promotion header on the product page!");

        //Проверяем, что присутствует FlipClock счётчик на страницу товара
        softAssert.assertTrue(stPromotions.flipClock.exists(),
                "Countdown type is not FlipClock on the product page!");

        sleep(2000);
        screenshot("500 ProductPage - Product page, Default");

        Utils.selectLanguage("ar");
        screenshot("502 ProductPage - Product page, Default (RTL)");
        Utils.shiftBrowserTab(0);
        basicPage.field_SearchOnTop.setValue(productCode).pressEnter();
        basicPage.productTemplate.selectOptionByValue("bigpicture_template");
        basicPage.saveAndGoToStorefront_ProductPage(2);
        screenshot("504 ProductPage - Product page, BigPicture");
        Utils.selectLanguage("ar");
        screenshot("506 ProductPage - Product page, BigPicture (RTL)");
        basicPage.selectProductTemplate("abt__ut2_bigpicture_flat_template");
        basicPage.saveAndGoToStorefront_ProductPage(3);
        screenshot("508 ProductPage - Product page, BigPictureFlat");
        Utils.selectLanguage("ar");
        screenshot("510 ProductPage - Product page, BigPictureFlat (RTL)");
        basicPage.selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        basicPage.saveAndGoToStorefront_ProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoCenter();
        screenshot("512 ProductPage - Product page, Gallery");
        Utils.selectLanguage("ar");
        $(".ab__deal_of_the_day").scrollIntoCenter();
        screenshot("514 ProductPage - Product page, Gallery (RTL)");
        basicPage.selectProductTemplate("abt__ut2_cascade_gallery_template");
        basicPage.saveAndGoToStorefront_ProductPage(5);
        screenshot("516 ProductPage - Product page, CascadeGallery");
        Utils.selectLanguage("ar");
        screenshot("518 ProductPage - Product page, CascadeGallery (RTL)");
        basicPage.selectProductTemplate("abt__ut2_three_columns_template");
        basicPage.saveAndGoToStorefront_ProductPage(6);
        screenshot("520 ProductPage - Product page, Three-columned");
        Utils.selectLanguage("ar");
        screenshot("522 ProductPage - Product page, Three-columned (RTL)");
        softAssert.assertAll();
    }
}