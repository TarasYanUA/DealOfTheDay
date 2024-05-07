import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/* Проверка модуля "АВ: Расширенные промо-акции" v3.10.3 + тема UniTheme2 (Ult & MV). */

public class TestRunner {
    public static final String BASIC_URL = "https://trs.test.abt.team/4181ultru/admin.php?dispatch=addons.manage";

    @BeforeClass
    public void openBrowser() {
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = false; //не закрываем браузер пока ведём разработку
        Configuration.screenshots = true; //делаем скриншоты при падении
        WebDriverRunner.getWebDriver().manage().window().maximize(); //окно браузера на весь экран
        open(BASIC_URL);

/*        $(".signin-modal__form-field.cm-focus").click();
        $(".signin-modal__form-field.cm-focus").sendKeys("vtv.www@gmail.com");
        $("#password").click();
        $("#password").sendKeys("vtv.www");*/

        $(".btn.btn-primary").click();
        $("#bp_off_bottom_panel").click();
    }

    @AfterClass
    public void closeBrowser() {
        Selenide.closeWebDriver();
    }

    public void selectLanguage_RTL() {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='ar']").click();
        makePause();
    }
    public void selectLanguage_RU() {
        $("a[id*='_wrap_language_']").hover().click();
        $(".ty-select-block__list-item a[data-ca-name='ru']").click();
    }
    public void shiftBrowserTab(int tabNumber){
        getWebDriver().getWindowHandle(); switchTo().window(tabNumber);
    }
    public void makePause(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearBothFieldsAvailable(){
        PromotionSettings promotionSettings = new PromotionSettings();
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.setting_AvailableFrom.clear();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.setting_AvailableTill.clear();
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
    }
}