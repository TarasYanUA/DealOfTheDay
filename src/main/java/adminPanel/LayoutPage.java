package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LayoutPage {
    public LayoutPage(){super();}

    public SelenideElement layout_LightV2 = $x("//a[contains(text(), '(Light v2)')]");
    SelenideElement gearwheelOfActiveLayout = $(".with-menu.active .dropdown-toggle");
    SelenideElement button_makeByDefault = $(".with-menu.active a[href*='block_manager.set_default_layout']");
    public SelenideElement layout_TabHomePage = $x("//a[text()='Домашняя страница']");

    public void setLayoutAsDefault() {
        gearwheelOfActiveLayout.hover().click();
        if ($(".with-menu.active a[href*='block_manager.set_default_layout']").exists()) {
            button_makeByDefault.click();
            Selenide.sleep(1500);
        }
    }

    public void switchOffBlock_DealOfTheDay() {  //Выключаем  блок "Товар дня"
        if (!$("div.block-off[data-ca-block-name=\"AB: Товар дня\"]").exists())
            $("div[data-ca-block-name=\"AB: Товар дня\"]").$(".cs-icon--type-off").click();
    }

    public void addNewBlock(){
        String layoutID = $x("//div[@title='AB: Товар дня']/../..").getAttribute("id");
        $(By.id(layoutID)).$(".cs-icon--type-plus").hover().click();
        $(By.id(layoutID)).$(".bm-action-add-block").click();
    }

    public SelenideElement blockProperties = $("div[data-ca-block-name=\"MultiBlock - AutoTest\"] .bm-action-properties.action");
    public SelenideElement button_SaveBlockProperties = $("input[name=\"dispatch[block_manager.update_block]\"]");
    public SelenideElement multiBlock = $(".bmicon-ab--multi-deal-of-the-day");
    public SelenideElement blockName = $("input[id$='__multi_deal_of_the_day_name']");
    public SelenideElement tab_CreateNewBlock = $("li[id^='create_new_blocks']");
    public SelenideElement tab_Content = $("li[id^='block_contents_']");
    public SelenideElement tab_BlockSettings = $("li[id^='block_settings_']");
    public SelenideElement button_AddPromotionsToBlock = $("a[id^='opener_picker_objects']");
    public SelenideElement button_AddAndCloseSelectedPromotions = $("input[value=\"Добавить промо-акции и закрыть\"]");
    public SelenideElement setting_DoNotScrollAutomatically = $("input[id$='_ab__multi_deal_of_the_day_properties_not_scroll_automatically']");
    public SelenideElement setting_ItemQuantity = $("input[id$='multi_deal_of_the_day_properties_item_quantity']");
    public SelenideElement setting_HideAddToCart = $("input[id$='multi_deal_of_the_day_properties_hide_add_to_cart_button']");
    public SelenideElement setting_DisplayPromotionCountdown = $("input[id$='multi_deal_of_the_day_properties_ab__dotd_enable_countdown_timer']");
    public SelenideElement button_CreateBlock = $("input[name='dispatch[block_manager.update_block]']");


    public void createBlock_MultiDealOfTheDay(String promotionID) {
        if (!$x("//div[@title=\"MultiBlock - AutoTest\"]").exists()) {
            addNewBlock();
            $(".ui-dialog-title").shouldBe(Condition.enabled);
            if ($("button.close.cm-notification-close[data-dismiss='alert']").exists())
                $("button.close.cm-notification-close[data-dismiss='alert']").click();
            tab_CreateNewBlock.click();
            multiBlock.click();
            $("#ui-id-2").shouldBe(Condition.enabled);
            blockName.setValue("MultiBlock - AutoTest");
            tab_Content.click();
            button_AddPromotionsToBlock.click();
            $("#ui-id-3").shouldBe(Condition.enabled);
            $(("input[id^='checkbox_id_" + promotionID)).click();
            button_AddAndCloseSelectedPromotions.click();
            button_CreateBlock.click();
        }
    }
}