package adminPanel;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.*;

public class CsCartSettings {
    public CsCartSettings(){super();}

    public SelenideElement button_Save = $(".btn.btn-primary.cm-submit");
    public SelenideElement popupWindow = $(".ui-dialog-title");
    public SelenideElement button_Storefront = $(".icon-shopping-cart");
    public SelenideElement gearWheelOnTop = $(".dropdown-icon--tools");
    public SelenideElement button_Preview = $x("//a[contains(text(), 'Предпросмотр')]");
    public SelenideElement menuAddons = $("#elm_menu_addons");
    private SelenideElement menuMarketing = $("a[href='#marketing']");
    private SelenideElement section_PromotionsAndDiscounts = $("a[href$='dispatch=promotions.manage']");
    public SelenideElement sectionDownloadedAddons = $("#elm_menu_addons_downloaded_add_ons");
    public SelenideElement menuOfExtPromotions = $("tr#addon_ab__deal_of_the_day button.btn.dropdown-toggle");
    public SelenideElement sectionGeneralSettings = $("div.nowrap a[href$='addon=ab__deal_of_the_day']");
    private SelenideElement tab_Settings = $("#settings");
    public SelenideElement field_SearchOnTop = $(".search__input--collapse");
    public SelenideElement productTemplate = $("#elm_details_layout");

    public SelenideElement menuProducts = $x("//li[@class='dropdown nav__header-main-menu-item ']//a[@href='#products']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement menuSettings = $(".dropdown-toggle.settings");
    public SelenideElement sectionAppearance = $("#elm_menu_settings_Appearance");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");
    public SelenideElement category_Notebooks = $(".table-wrapper a[href$='category_id=169']");

    private void navigateToAddonsPage(){
        menuAddons.hover();
        sectionDownloadedAddons.click();
    }
    public AddonSettings navigateToAddonSettings(){
        navigateToAddonsPage();
        menuOfExtPromotions.click();
        sectionGeneralSettings.click();
        tab_Settings.click();
        return new AddonSettings();
    }
    public void navigateToAppearanceSettings(){
        menuSettings.hover();
        sectionAppearance.click();
    }
    public PromotionSettings navigateToPromotionSettings(){
        menuMarketing.hover();
        section_PromotionsAndDiscounts.click();
        return new PromotionSettings();
    }
    public void navigateToCategoryPage(){
        menuProducts.hover();
        sectionCategories.click();
    }


    //Страница "Дизайн -- Макеты"
    private SelenideElement menuDesign = $("#elm_menu_design");
    private SelenideElement sectionLayouts = $("#elm_menu_design_layouts");
    public MultiBlock navigateToSectionLayouts(){
        menuDesign.hover();
        sectionLayouts.click();
        return new MultiBlock();
    }
    public SelenideElement layout_LightV2 = $x("//a[contains(text(), 'UniTheme 2 (Light v2)')]");

    private SelenideElement gearwheelOfActiveLayout = $(".with-menu.active .dropdown-toggle");
    private SelenideElement button_makeByDefault = $(".with-menu.active a[href*='block_manager.set_default_layout']");
    public void setLayoutAsDefault() {
        gearwheelOfActiveLayout.hover().click();
        if ($(".with-menu.active a[href*='block_manager.set_default_layout']").exists()) {
            button_makeByDefault.click();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public SelenideElement layout_TabHomePage = $x("//a[text()='Домашняя страница']");
    public void switchOffBlock_DealOfTheDay(){  //Выключаем  блок "Товар дня"
        if(!$("div.block-off[data-ca-block-name=\"AB: Товар дня\"]").exists()){
            $("div[data-ca-block-name=\"AB: Товар дня\"]").$(".icon-off").click();
        }
    }
}