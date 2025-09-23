package testRunner;

import adminPanel.BasicPage;
import adminPanel.PromotionSettings;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Utils {

    public static void selectLanguage(String ruEnAr) {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='" + ruEnAr + "']").click();
        $("a[id*='_wrap_language_']").hover();
    }

    public static void shiftBrowserTab(int tabNumber) {
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
    }

    public static void clearBothFieldsAvailable() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = new PromotionSettings();
        if (!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();
        }
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.setting_AvailableFrom.clear();
        sleep(1500);
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.setting_AvailableTill.clear();
        basicPage.button_Save.click();
    }
}