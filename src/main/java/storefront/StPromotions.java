package storefront;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class StPromotions {
    public StPromotions(){super();}

    public SelenideElement block_DealOfTheDay = $(".ab__deal_of_the_day");
    public SelenideElement blockButton_More = $(".pd-promotion__buttons .ty-btn__secondary");
    public SelenideElement blockButton_AllPromotions = $(".pd-promotion__buttons .ty-btn__text");

    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'Купите фотоаппарат')]");
    public SelenideElement chooseAnyProduct = $(".ut2-gl__item");
    public SelenideElement breadcrumb_Computers = $("a[href$='/kompyutery/'].ty-breadcrumbs__a");

    public SelenideElement button_QuickView = $(".ut2-icon-baseline-visibility");
    public SelenideElement button_ClosePopupWindow = $(".ui-icon-closethick");
    public SelenideElement categoryTemplate_WithoutOptions = $(".ty-icon-products-without-options");
    public SelenideElement category_CompactList = $(".ty-sort-container__views-a .ty-icon-short-list");

    //Проверки
    public SelenideElement filterByProducts = $x("//div[contains(text(), 'Фильтры товаров')]");
    public SelenideElement productBlock = $("#promotions_view_pagination_contents");
    public ElementsCollection labelOnCategoryPage = $$(".ab_dotd_product_label");
    public ElementsCollection promotionOnCategoryPage = $$(".ab-dotd-category-promo");
    public SelenideElement promotionHeader = $(".ab__deal_of_the_day");
    public SelenideElement promotionHeaderInQuickView = $(".ui-dialog-titlebar");
    public SelenideElement promotionHeaderOnPromoPage = $(".ab__dotd_promotion-main_info");
    public SelenideElement countdown = $(".ab__dotd_promotion-timer");
    public SelenideElement flipClock = $(".flip-clock-wrapper");
    public SelenideElement text_OnlyToday = $x("//div[contains(text(), 'Только сегодня')]");
    public SelenideElement text_DaysLeftBeforeStart = $x("//div[contains(text(), 'До начала')]");
    public SelenideElement text_PromotionHasExpired = $x("//div[contains(text(), 'Акция завершена')]");
    public ElementsCollection promotionsPerPage = $$(".ab__dotd_promotions-item");
    public SelenideElement highlight = $(".ab__dotd_highlight");
    public ElementsCollection promotionProducts = $$(".ut2-gl__item");
}
