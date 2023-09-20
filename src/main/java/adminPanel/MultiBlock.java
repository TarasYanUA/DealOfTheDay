package adminPanel;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MultiBlock {
    public MultiBlock(){super();}
    private SelenideElement addNewBlock_gearwheel = $("#grid_552 .icon-plus");
    private SelenideElement addNewBlock_button = $("#grid_552 .bm-action-add-block");
    public void addNewBlock(){
        addNewBlock_gearwheel.hover().click();
        addNewBlock_button.click();
    }
    public SelenideElement multiBlock = $(".bmicon-ab--multi-deal-of-the-day");
    public SelenideElement blockName = $("input[id$='__multi_deal_of_the_day_name']");
    public SelenideElement tab_CreateNewBlock = $("li[id^='create_new_blocks']");
    public SelenideElement tab_Content = $("li[id^='block_contents_']");
    public SelenideElement tab_BlockSettings = $("li[id^='block_settings_']");
    public SelenideElement button_AddPromotionsToBlock = $("a[id^='opener_picker_objects']");
    public void chooseThreePromotionsForBlock(){
        $("#checkbox_id_28").click();
        $("#checkbox_id_29").click();
        $("#checkbox_id_30").click();
        $("input.cm-dialog-closer").click();
    }
    public SelenideElement setting_ShowPrice = $("input[id$='multi_deal_of_the_day_properties_show_price']");
    public SelenideElement setting_EnableQuickView = $("input[id$='multi_deal_of_the_day_properties_enable_quick_view']");
    public SelenideElement setting_ItemQuantity = $("input[id$='multi_deal_of_the_day_properties_item_quantity']");
    public SelenideElement setting_HideAddToCart = $("input[id$='multi_deal_of_the_day_properties_hide_add_to_cart_button']");
    public SelenideElement setting_DisplayPromotionCountdown = $("input[id$='multi_deal_of_the_day_properties_ab__dotd_enable_countdown_timer']");
    public SelenideElement button_CreateBlock = $("input[name='dispatch[block_manager.update_block]']");
}