package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import storefront.StPromotions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BasicPage implements CheckMenuToBeActive {
    public BasicPage() {
        super();
    }

    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement popupWindow = $(".ui-dialog-title");
    public SelenideElement gearWheelOnTop = $(".nav__actions-bar .dropdown-icon--tools");
    public SelenideElement button_Preview = $x("//a[contains(text(), 'Предпросмотр')]");
    private SelenideElement button_Languages = $("a[id*='_wrap_content']");
    private SelenideElement russianLanguage = $("#content_top_navigation .popup-icons a[href$='descr_sl=ru']");


    public StPromotions navigateTo_Storefront() {
        String currentUrl = WebDriverRunner.url();
        String[] url = currentUrl.split("admin.php");
        executeJavaScript("window.open('" + url[0] + "')");
        return new StPromotions();
    }

    public void chooseRussianLanguage() {
        closeAllNotifications();
        button_Languages.click();
        russianLanguage.shouldBe(Condition.visible).click();
    }

    private SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    private SelenideElement section_Categories = $("#products_categories");
    public SelenideElement category_Notebooks = $(".table-wrapper a[href$='category_id=169']");

    private SelenideElement menu_Marketing = $("a[href$='dispatch=promotions.manage'].main-menu-1__link");
    private SelenideElement section_PromotionsAndDiscounts = $("#marketing_promotions");

    private SelenideElement menu_Website = $("a[href$='dispatch=themes.manage'].main-menu-1__link");
    private SelenideElement section_Themes = $("#website_themes");
    private SelenideElement section_Layouts = $(".nav__actions-bar a[href$='block_manager.manage']");

    public SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    public SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");
    public SelenideElement gearwheelOfAddon = $("tr#addon_ab__deal_of_the_day button.btn.dropdown-toggle");
    public SelenideElement sectionOfAddon_GeneralSettings = $("div.nowrap a[href$='addon=ab__deal_of_the_day']");
    private SelenideElement tab_Settings = $("#settings");
    public SelenideElement field_SearchOnTop = $(".cm-autocomplete-off.search__input");
    public SelenideElement productTemplate = $("#elm_details_layout");

    SelenideElement menu_Settings = $("#administration");
    SelenideElement section_GeneralSettings = $("a[href$='section_id=General']");
    SelenideElement section_Appearance = $("a[href$='section_id=Appearance']");
    SelenideElement setting_QuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");


    public void navigateTo_CategoryPage() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
    }

    public void navigateToCategoryPage_Notebooks() {
        if ($("span[alt='Свернуть список'].cm-combination.hidden").exists()) {
            $x("//span[text()='Магазин: CS-Cart']/..//span[contains(@class, 'icon-caret-right')]").click();
            $x("//a[contains(@href, 'category_id=166')]/..//span[contains(@class, 'icon-caret-right')]").click();
            $x("//a[contains(@href, 'category_id=167')]/..//span[contains(@class, 'icon-caret-right')]").click();
        }
        category_Notebooks.click();
    }

    public PromotionSettings navigateTo_PromotionSettings() {
        checkMenuToBeActive("dispatch=promotions.manage", menu_Marketing);
        section_PromotionsAndDiscounts.click();
        return new PromotionSettings();
    }

    public LayoutPage navigateToSectionLayouts() {
        checkMenuToBeActive("dispatch=themes.manage", menu_Website);
        section_Themes.click();
        section_Layouts.click();
        return new LayoutPage();
    }

    private void navigateTo_DownloadedAddonsPage() {
        checkMenuToBeActive("dispatch=addons.manage", menu_Addons);
        section_DownloadedAddons.click();
    }

    public AddonSettings navigateTo_AddonSettings() {
        navigateTo_DownloadedAddonsPage();
        gearwheelOfAddon.click();
        sectionOfAddon_GeneralSettings.click();
        tab_Settings.click();
        return new AddonSettings();
    }

    public void navigateTo_AppearanceSettingsAndQuickViewOn() {
        menu_Settings.click();
        section_GeneralSettings.click();
        section_Appearance.click();
        if (!setting_QuickView.isSelected()) {
            setting_QuickView.click();
            button_Save.click();
        }
    }

    public void selectProductTemplate(String templateValue) {
        getWebDriver().getWindowHandle();
        switchTo().window(0);
        productTemplate.selectOptionByValue(templateValue);
    }

    public void savePage() {
        button_Save.click();
        sleep(1500);
    }

    public void navigateToStorefront(int tabNumber) {
        sleep(1500);
        gearWheelOnTop.click();
        button_Preview.click();
        getWebDriver().getWindowHandle();
        switchTo().window(tabNumber);
        if ($(".cm-btn.cm-btn-success").exists())
            $(".cm-btn.cm-btn-success").click();
    }

    public static void closeAllNotifications() {
        while (!$$(".cm-notification-close").isEmpty()) {
            $$(".cm-notification-close").first().click();
            sleep(200);
        }
    }
}