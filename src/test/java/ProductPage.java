import adminPanel.AddonSettings;
import adminPanel.PromotionSettings;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import adminPanel.BasicPage;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import testRunner.TestRunner;
import testRunner.Utils;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

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
        if (promotionSettings.setting_UseAvailablePeriod.isSelected()) {  //убираем период доступности, чтобы промо-акция всегда отображалась
            promotionSettings.setting_UseAvailablePeriod.click();
        }
        if (promotionSettings.setting_StopOtherRules.isSelected()) {
            promotionSettings.setting_StopOtherRules.click();
        }
        //Вкладка "АВ: Расширенные промо-акции" у промо-акции
        promotionSettings.tab_ABExtPromotions.click();
        if (promotionSettings.check_DisplayCountdownOnProductPage.isSelected()) {
            promotionSettings.check_DisplayCountdownOnProductPage.click();
        }

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
        basicPage.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        basicPage.productTemplate.selectOptionByValue("default_template");
        basicPage.gearWheelOnTop.click();
        basicPage.button_Preview.click();
        Utils.shiftBrowserTab(1);
        $(".cm-btn.cm-btn-success").click();
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
        basicPage.field_SearchOnTop.click();
        basicPage.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        basicPage.productTemplate.selectOptionByValue("bigpicture_template");
        goToProductPage(2);
        screenshot("504 ProductPage - Product page, BigPicture");
        Utils.selectLanguage("ar");
        screenshot("506 ProductPage - Product page, BigPicture (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_flat_template");
        goToProductPage(3);
        screenshot("508 ProductPage - Product page, BigPictureFlat");
        Utils.selectLanguage("ar");
        screenshot("510 ProductPage - Product page, BigPictureFlat (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        goToProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("512 ProductPage - Product page, Gallery");
        Utils.selectLanguage("ar");
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("514 ProductPage - Product page, Gallery (RTL)");
        selectProductTemplate("abt__ut2_three_columns_template");
        goToProductPage(5);
        screenshot("516 ProductPage - Product page, Three-columned");
        Utils.selectLanguage("ar");
        screenshot("518 ProductPage - Product page, Three-columned (RTL)");
        softAssert.assertAll();
    }

    public void selectProductTemplate(String templateValue) {
        Utils.shiftBrowserTab(0);
        BasicPage basicPage = new BasicPage();
        basicPage.productTemplate.selectOptionByValue(templateValue);
    }

    public void goToProductPage(int tabNumber) {
        BasicPage basicPage = new BasicPage();
        basicPage.button_Save.click();
        basicPage.gearWheelOnTop.click();
        basicPage.button_Preview.click();
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
        sleep(2000);
    }
}