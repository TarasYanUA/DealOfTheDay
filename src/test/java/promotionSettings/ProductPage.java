package promotionSettings;

import generalSettings.TestRunner;
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
* Скрыть блок товаров --        нет
* Отображать счётчик на странице товара --      да
*/

public class ProductPage extends TestRunner {
    @Test(priority = 2)
    public void check_ProductPage(){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue("M0219A3GX3").sendKeys(Keys.ENTER);
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
        screenshot("112 GeneralSettings_Var1_Default - Product page, Default");
        selectLanguage_RTL();
        screenshot("114 GeneralSettings_Var1_Default - Product page, Default (RTL)");
        String productCode = $("span[id^='product_code_']").getText(); //Берём код товара
        shiftBrowserTab(0);
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue(productCode).sendKeys(Keys.ENTER);
        csCartSettings.productTemplate.selectOptionByValue("bigpicture_template");
        goToProductPage(2);
        screenshot("116 GeneralSettings_Var1_Default - Product page, BigPicture");
        selectLanguage_RTL();
        screenshot("118 GeneralSettings_Var1_Default - Product page, BigPicture (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_flat_template");
        goToProductPage(3);
        screenshot("120 GeneralSettings_Var1_Default - Product page, BigPictureFlat");
        selectLanguage_RTL();
        screenshot("122 GeneralSettings_Var1_Default - Product page, BigPictureFlat (RTL)");
        selectProductTemplate("abt__ut2_bigpicture_gallery_template");
        goToProductPage(4);
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("124 GeneralSettings_Var1_Default - Product page, Gallery");
        selectLanguage_RTL();
        $(".ab__deal_of_the_day").scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
        screenshot("126 GeneralSettings_Var1_Default - Product page, Gallery (RTL)");
        selectProductTemplate("abt__ut2_three_columns_template");
        goToProductPage(5);
        screenshot("128 GeneralSettings_Var1_Default - Product page, Three-columned");
        selectLanguage_RTL();
        screenshot("130 GeneralSettings_Var1_Default - Product page, Three-columned (RTL)");
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
