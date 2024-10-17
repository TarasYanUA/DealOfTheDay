package storefront;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class StPromotions {
    public StPromotions() {
        super();
    }

    public SelenideElement block_DealOfTheDay = $(".ab__deal_of_the_day");
    public SelenideElement blockButton_More = $(".pd-promotion__buttons a[title='Подробнее']");
    public SelenideElement blockButton_AllPromotions = $(".pd-promotion__buttons .ty-btn__text");

    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'Купите фотоаппарат')]");
    public SelenideElement chooseAnyProduct = $(".ut2-gl__item");
    public SelenideElement button_QuickView = $("a[data-ca-target-id='product_quick_view']");
    public SelenideElement button_ClosePopupWindow = $(".ui-icon-closethick");
    public SelenideElement categoryTemplate_WithoutOptions = $(".ut2-icon-products-without-options");
    public SelenideElement categoryTemplate_CompactList = $(".ut2-icon-short-list");
    public SelenideElement categoryTemplate_Grid = $(".ut2-icon-products-multicolumns");

    //Проверки
    public SelenideElement filterByProducts = $x("//span[contains(text(), 'Фильтры товаров')]");
    public SelenideElement productBlock = $(By.id("promotion_products"));
    public ElementsCollection labelOnCategoryPage = $$(".ab_dotd_product_label");
    public ElementsCollection promotionOnCategoryPage = $$(".ab-dotd-category-promo");
    public SelenideElement promotionHeader = $(".ab__deal_of_the_day");
    public SelenideElement promotionHeaderInQuickView = $(".ui-dialog-titlebar");
    public SelenideElement promotionHeaderOnPromoPage = $(".ab__dotd_promotion-main_info");
    public SelenideElement countdown = $(".ab__dotd_promotion-timer");
    public SelenideElement javaClock = $(".js-counter");
    public SelenideElement flipClock = $(".flip-clock-wrapper");
    public SelenideElement text_OnlyToday = $x("//div[contains(text(), 'Только сегодня')]");
    public SelenideElement text_DaysLeftBeforeStart = $x("//div[contains(text(), 'До начала')]");
    public SelenideElement text_PromotionHasExpired = $x("//div[contains(text(), 'Акция завершена')]");
    public ElementsCollection promotionsPerPage = $$(".ab__dotd_promotions-item");
    public SelenideElement highlight = $(".ab__dotd_highlight");
    public ElementsCollection promotionProducts = $$(".ut2-gl__item");
    public ElementsCollection button_MoreProductsFromCategory = $$(".ab-dotd-more-icon");

    //Проверки в блоках
    public SelenideElement blockTitle = $(".pd-promotion__title");
    public SelenideElement blockDescription = $(".pd-promotion-descr");
    public ElementsCollection blockProducts = $$(".ab__deal_of_the_day .ut2-gl__body");
}
