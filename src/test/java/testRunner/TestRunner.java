package testRunner;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import storefront.CollectAssertMessages;

import static com.codeborne.selenide.Selenide.*;

/* Проверка модуля "АВ: Расширенные промо-акции" v3.12.0 + тема UniTheme2 (Ult & MV). */

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4201ultru/admin.php?dispatch=addons.manage";
    private SoftAssert softAssert;

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        open(BASIC_URL);
        Configuration.screenshots = true; //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        Configuration.savePageSource = false; //не создавать html файлы при создании скриншотов

        softAssert = new SoftAssert();
        CollectAssertMessages.setSoftAssertions(softAssert);

        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
        Utils.closeAllNotifications();
        Selenide.sleep(1000);
    }

    @AfterClass
    public void closeBrowser() {
        softAssert = CollectAssertMessages.getSoftAssertions();

        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println("\nОшибки в asserts:");
            System.out.println(e.getMessage());
        }

        Selenide.sleep(1500);
        Selenide.closeWebDriver();
    }
}