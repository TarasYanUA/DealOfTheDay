package testRunner;

import adminPanel.BasicPage;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/* Проверка модуля "АВ: Расширенные промо-акции" v3.10.5 + тема UniTheme2 (Ult & MV). */

public class TestRunner {
    public static final String BASIC_URL = "https://abd-d46edb0243.demos.abt.team/admin.php?dispatch=addons.manage";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.screenshots = true; //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        Configuration.savePageSource = false; //не создавать html файлы при создании скриншотов

        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
        if ($(".cm-notification-close").isDisplayed())
            $(".cm-notification-close").click();
        Selenide.sleep(1000);
    }

    @AfterClass
    public void closeBrowser() {
        Selenide.sleep(1500);
        Selenide.closeWebDriver();
    }
}