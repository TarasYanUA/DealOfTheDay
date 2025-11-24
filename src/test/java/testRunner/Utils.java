package testRunner;

import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Utils {

    public static void selectLanguage(String ruEnAr) {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='" + ruEnAr + "']").click();
        $("a[id*='_wrap_language_']").hover();
        sleep(1500);
    }

    public static void shiftBrowserTab(int tabNumber) {
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
    }

    public static void setCheckboxState(SelenideElement checkbox, boolean shouldBeChecked) {
        if (checkbox.isSelected() != shouldBeChecked) {
            checkbox.scrollIntoCenter().click();
        }
    }

    public static void clearBothFieldsAvailable() {
        BasicPage basicPage = new BasicPage();
        PromotionSettings promotionSettings = new PromotionSettings();
        setCheckboxState(promotionSettings.setting_UseAvailablePeriod, true);
        promotionSettings.setting_AvailableFrom.clear();
        sleep(1500);
        promotionSettings.setting_AvailableTill.clear();
        basicPage.button_Save.click();
    }

    public static void waitForSpinnerDisappear() {
        $("div#ajax_loading_box[style=\"display: block;\"]").shouldBe(Condition.disappear, Duration.ofSeconds(10));
        sleep(2000);
    }

    public static void scrollToTabAndClick(SelenideElement element) {
        element.scrollIntoCenter().click();
        sleep(1500);
    }

    public static void closeAllNotifications() {
        while (!$$(".cm-notification-close").isEmpty()) {
            $$(".cm-notification-close").first().click();
            sleep(200);
        }
    }
}