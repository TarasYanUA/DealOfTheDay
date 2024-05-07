import adminPanel.AddonSettings;
import adminPanel.PromotionSettings;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import adminPanel.CsCartSettings;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
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
    public void setConfiguration_ProductPage(){
        CsCartSettings csCartSettings = new CsCartSettings();
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
        if(promotionSettings.check_DisplayCountdownOnProductPage.isSelected()){
            promotionSettings.check_DisplayCountdownOnProductPage.click(); }

        //Задаём настройки модуля
        AddonSettings addonSettings = csCartSettings.navigateTo_AddonSettings();
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.button_SaveSettings.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_ProductPage")
    public void check_ProductPage(){
        CsCartSettings csCartSettings = new CsCartSettings();
        String productCode = "M0219A3GX3";
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        csCartSettings.productTemplate.selectOptionByValue("default_template");
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(1);
        selectLanguage_RU();
        StPromotions stPromotions = new StPromotions();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что шапка промо-акции присутствует на странице товара
        softAssert.assertTrue(stPromotions.promotionHeader.exists(),
                "There is no promotion header on the product page!");
        //Проверяем, что присутствует FlipClock счётчик на страницу товара
        softAssert.assertTrue(stPromotions.flipClock.exists(),
                "Countdown type is not FlipClock on the product page!");
        makePause();
        screenshot("500 ProductPage - Product page, Default");
        selectLanguage_RTL();
        screenshot("502 ProductPage - Product page, Default (RTL)");
        shiftBrowserTab(0);
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        csCartSettings.productTemplate.selectOptionByValue("bigpicture_template");
        goToProductPage(2);
        screenshot("504 ProductPage - Product page, BigPicture");
        selectLanguage_RTL();
        screenshot("506 ProductPage - Product page, BigPicture (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_flat_template");
        goToProductPage(3);
        screenshot("508 ProductPage - Product page, BigPictureFlat");
        selectLanguage_RTL();
        screenshot("510 ProductPage - Product page, BigPictureFlat (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        goToProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("512 ProductPage - Product page, Gallery");
        selectLanguage_RTL();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("514 ProductPage - Product page, Gallery (RTL)");
        selectProductTemplate("abt__ut2_three_columns_template");
        goToProductPage(5);
        screenshot("516 ProductPage - Product page, Three-columned");
        selectLanguage_RTL();
        screenshot("518 ProductPage - Product page, Three-columned (RTL)");
        softAssert.assertAll();
    }

    public void selectProductTemplate(String templateValue) {
        shiftBrowserTab(0);
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.productTemplate.selectOptionByValue(templateValue);
    }
    public void goToProductPage(int tabNumber){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_Preview.click();
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
        makePause();
    }
}
