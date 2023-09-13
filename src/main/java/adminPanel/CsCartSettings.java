package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

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
    public SelenideElement sectionProducts = $x("//span[text()='Товары']");
    public SelenideElement sectionCategories = $("a[href$='categories.manage']");
    public SelenideElement menuSettings = $(".dropdown-toggle.settings");
    public SelenideElement sectionAppearance = $("#elm_menu_settings_Appearance");
    public SelenideElement settingMiniThumbnailAsGallery = $("#field___thumbnails_gallery_147");
    public SelenideElement settingQuickView = $x("//input[contains(@id, 'field___enable_quick_view_')]");
    public SelenideElement category_Notebooks = $(".table-wrapper a[href$='category_id=169']");

    public void navigateToAddonsPage(){
        menuAddons.hover();
        sectionDownloadedAddons.click();
    }
    public void navigateToAppearanceSettings(){
        menuSettings.hover();
        sectionAppearance.click();
    }
    public AddonSettings navigateToAddonSettings(){
        navigateToAddonsPage();
        menuOfExtPromotions.click();
        sectionGeneralSettings.click();
        tab_Settings.click();
        return new AddonSettings();
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
    public SelenideElement menuDesign = $("#elm_menu_design");
    public SelenideElement sectionLayouts = $("#elm_menu_design_layouts");
    public SelenideElement layout_TabProducts = $x("//a[contains(@href, 'selected_location')][text()='Товары']");
    public SelenideElement layout_GearwheelOfBlockPopular = $("#snapping_714 div.bm-action-properties");
    public SelenideElement layout_GearwheelOfBlockHits = $("#snapping_715 div.bm-action-properties");
    public SelenideElement layout_BlockTemplate = $("select[id*='products_template']");
    public SelenideElement layout_ButtonSaveBlock = $("input[name='dispatch[block_manager.update_block]']");
    public SelenideElement layoutBlock_TabContent = $("li[id*='block_contents'] a");
    public SelenideElement layout_FieldFilling = $("select[id*='content_items_filling']");
    public SelenideElement layout_FieldMaxLimit = $("input[id*='content_items_properties_items_limit']");

    public Select getLayout_BlockTemplate(){return new Select(layout_BlockTemplate);}
    public void selectBlockTemplate(String value){
        getLayout_BlockTemplate().selectByValue(value);
    }
    public Select getLayout_FieldFilling(){return new Select(layout_FieldFilling);}
    public void selectLayout_FieldFilling(){
        getLayout_FieldFilling().selectByValue("newest");}
    public void clickAndType_Layout_FieldMaxLimit(){
        layout_FieldMaxLimit.click();
        layout_FieldMaxLimit.clear();
        layout_FieldMaxLimit.setValue("4");
    }
}