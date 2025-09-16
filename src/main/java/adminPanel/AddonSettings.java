package adminPanel;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AddonSettings {
    public AddonSettings(){super();}

    public SelenideElement setting_CountdownTo = $("select[id^='addon_option_ab__deal_of_the_day_count_to_']");
    public SelenideElement setting_CountdownType = $("select[id^='addon_option_ab__deal_of_the_day_countdown_type_']");
    public SelenideElement setting_MaximumHeightOfDescription = $("input[id^='addon_option_ab__deal_of_the_day_max_height_']");
    public SelenideElement setting_PromotionsPerPage = $("input[id^='addon_option_ab__deal_of_the_day_promotions_per_page_']");
    public SelenideElement setting_HighlightingThePromotion = $("select[id^='addon_option_ab__deal_of_the_day_highlight_when_left_']");
    public SelenideElement setting_AmountOfDisplayedPromotionsInProductLists = $("select[id^='addon_option_ab__deal_of_the_day_amount_of_promos_in_prods_lists_']");
    public SelenideElement setting_ShowExpiredPromotions = $("input[id^='addon_option_ab__deal_of_the_day_ab__show_expired_promos_']");
    public SelenideElement setting_ShowAwaitingPromotions = $("input[id^='addon_option_ab__deal_of_the_day_ab__show_awaited_promos_']");
    public SelenideElement button_SaveSettings = $(".cm-addons-save-settings");
}