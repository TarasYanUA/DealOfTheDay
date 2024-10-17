package adminPanel;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import storefront.StPromotions;

import static com.codeborne.selenide.Selenide.*;

public class CsCartSettings implements CheckMenuToBeActive {
    public CsCartSettings() {
        super();
    }

    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement popupWindow = $(".ui-dialog-title");
    public SelenideElement gearWheelOnTop = $(".nav__actions-bar .dropdown-icon--tools");
    public SelenideElement button_Preview = $x("//a[contains(text(), 'Предпросмотр')]");

    public StPromotions navigateTo_Storefront() {
        String currentUrl = WebDriverRunner.url();
        String[] url = currentUrl.split("admin.php");
        executeJavaScript("window.open('" + url[0] + "')");
        return new StPromotions();
    }

    private SelenideElement menu_Products = $("a[href$='dispatch=products.manage'].main-menu-1__link");
    private SelenideElement section_Categories = $("#products_categories");

    private SelenideElement menu_Marketing = $("a[href$='dispatch=promotions.manage'].main-menu-1__link");
    private SelenideElement section_PromotionsAndDiscounts = $("#marketing_promotions");

    public SelenideElement menu_Addons = $("a[href$='dispatch=addons.manage'].main-menu-1__link");
    public SelenideElement section_DownloadedAddons = $("#addons_downloaded_add_ons");
    public SelenideElement gearwheelOfAddon = $("tr#addon_ab__deal_of_the_day button.btn.dropdown-toggle");
    public SelenideElement sectionOfAddon_GeneralSettings = $("div.nowrap a[href$='addon=ab__deal_of_the_day']");
    private SelenideElement tab_Settings = $("#settings");
    public SelenideElement field_SearchOnTop = $(".cm-autocomplete-off.search__input");
    public SelenideElement productTemplate = $("#elm_details_layout");

    private SelenideElement menu_Settings = $("#administration");
    private SelenideElement section_Appearance = $("a[href$='section_id=Appearance']");
    private SelenideElement section_GeneralSettings = $("a[href$='section_id=General']");
    public SelenideElement setting_QuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");
    public SelenideElement category_Notebooks = $(".table-wrapper a[href$='category_id=169']");

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

    public void navigateTo_AppearanceSettings() {
        menu_Settings.click();
        section_GeneralSettings.click();
        section_Appearance.click();
    }

    public PromotionSettings navigateTo_PromotionSettings() {
        checkMenuToBeActive("dispatch=promotions.manage", menu_Marketing);
        section_PromotionsAndDiscounts.click();
        return new PromotionSettings();
    }

    public void navigateTo_CategoryPage() {
        checkMenuToBeActive("dispatch=products.manage", menu_Products);
        section_Categories.click();
    }


    //Меню "Веб-сайт -- Темы -- Макеты"
    private SelenideElement menu_Website = $("a[href$='dispatch=themes.manage'].main-menu-1__link");
    private SelenideElement section_Themes = $("#website_themes");
    private SelenideElement section_Layouts = $(".nav__actions-bar a[href$='block_manager.manage']");

    public MultiBlock navigateToSectionLayouts() {
        checkMenuToBeActive("dispatch=themes.manage", menu_Website);
        section_Themes.click();
        section_Layouts.click();
        return new MultiBlock();
    }

    public SelenideElement layout_LightV2 = $x("//a[contains(text(), '(Light v2)')]");

    private SelenideElement gearwheelOfActiveLayout = $(".with-menu.active .dropdown-toggle");
    private SelenideElement button_makeByDefault = $(".with-menu.active a[href*='block_manager.set_default_layout']");

    public void setLayoutAsDefault() {
        gearwheelOfActiveLayout.hover().click();
        if ($(".with-menu.active a[href*='block_manager.set_default_layout']").exists()) {
            button_makeByDefault.click();
            Selenide.sleep(1500);
        }
    }

    public SelenideElement layout_TabHomePage = $x("//a[text()='Домашняя страница']");

    public void switchOffBlock_DealOfTheDay() {  //Выключаем  блок "Товар дня"
        if (!$("div.block-off[data-ca-block-name=\"AB: Товар дня\"]").exists()) {
            $("div[data-ca-block-name=\"AB: Товар дня\"]").$(".cs-icon--type-off").click();
        }
    }
}