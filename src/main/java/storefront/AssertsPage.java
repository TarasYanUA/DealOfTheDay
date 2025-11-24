package storefront;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class AssertsPage {
    public AssertsPage() {super();}

    SoftAssert softAssert = CollectAssertMessages.getSoftAssertions();

    public String filterByProducts = ".ut2-sidebox-important.ut2-filters";
    public String productBlock = "#promotion_products";
    public String labelOnCategoryPage = ".ab_dotd_product_label";
    public String promotionOnCategoryPage = ".ab-dotd-category-promo";
    public String promotionHeader = ".ab__deal_of_the_day";
    public String promotionHeaderInQuickView = ".ui-dialog-content .ab__deal_of_the_day";
    public String promotionHeaderOnPromoPage = ".ab__dotd_promotion-main_info";
    public String countdown = ".ab__dotd_promotion-timer";
    public String javaClock = ".js-counter";
    public String flipClock = ".flip-clock-wrapper";
    public String highlight = ".ab__dotd_highlight";
    public String promotionProducts = ".ut2-gl__item";
    public String button_MoreProductsFromCategory = ".ab-dotd-more-icon";
    public String blockTitle = ".pd-promotion__title";
    public String blockDescription = ".pd-promotion-descr";
    public String blockProducts = ".ab__deal_of_the_day .ut2-gl__body";
    public String buttonMoreInDescription = ".ab__dotd_more";
    public String pagination = "#ut2_pagination_block_bottom";
    public String blockButton_More = ".pd-promotion__buttons a[title='Подробнее']";
    public String blockButton_AllPromotions = ".pd-promotion__buttons .ty-btn__text";
    public String priceAtPromotionBlock = ".ab__deal_of_the_day .ty-list-price.ty-nowrap";
    public String buttonQuickViewAtPromotionBlock = ".ab__deal_of_the_day a[data-ca-target-id='product_quick_view']";
    public String buttonAddToCartAtPromotionBlock = ".ab__deal_of_the_day .ut2-icon-use_icon_cart";

    //Дополнительные проверки, что не входят в методы
    public SelenideElement text_OnlyToday = $x("//div[contains(text(), 'Только сегодня')]");
    public SelenideElement text_DaysLeftBeforeStart = $x("//div[contains(text(), 'До начала')]");
    public SelenideElement text_PromotionHasExpired = $x("//div[contains(text(), 'Акция завершена')]");
    public ElementsCollection promotionsPerPage = $$(".ab__dotd_promotions-item");


    public void assertElementPresence(String selector, String page, boolean shouldExist) {
        Map<String, String> presenceMessages = Map.ofEntries(
                Map.entry(filterByProducts, "There are no product filters "),
                Map.entry(productBlock, "There is no product block "),
                Map.entry(labelOnCategoryPage, "There is no promotion label "),
                Map.entry(promotionOnCategoryPage, "There is no any promotion "),
                Map.entry(promotionHeader, "There is no promotion header "),
                Map.entry(promotionHeaderInQuickView, "There is no promotion header in the quick view window!"),
                Map.entry(promotionHeaderOnPromoPage, "There is no promotion header on the promotion page!"),
                Map.entry(countdown, "There is no countdown "),
                Map.entry(javaClock, "Countdown type is not Javascript "),
                Map.entry(flipClock, "Countdown type is not FlipClock "),
                Map.entry(highlight, "There is no Highlighting of the promotion "),
                Map.entry(promotionProducts, "There are no products "),
                Map.entry(button_MoreProductsFromCategory, "There is no any button 'More products from category' "),
                Map.entry(blockTitle, "There is no title of the promotion "),
                Map.entry(blockDescription, "There is no description of the promotion in the block!"),
                Map.entry(blockProducts, "There are no products in the block!"),
                Map.entry(buttonMoreInDescription, "There is no button 'More' at the promotion description on the promotion page!"),
                Map.entry(pagination, "There is no pagination on the promotion list page!"),
                Map.entry(blockButton_More, "There is no button 'More' in the block!"),
                Map.entry(blockButton_AllPromotions, "There is no button 'All promotions' in the block!"),
                Map.entry(priceAtPromotionBlock, "There is no price at the products in the multi block!"),
                Map.entry(buttonQuickViewAtPromotionBlock, "There is no button 'Quick view' at the products in the multi block!"),
                Map.entry(buttonAddToCartAtPromotionBlock, "There is no button 'Add to cart' at the products in the multi block!")
                );

        Map<String, String> absenceMessages = Map.ofEntries(
                Map.entry(filterByProducts, "There are product filters but shouldn't "),
                Map.entry(productBlock, "There is a product block but shouldn't "),
                Map.entry(labelOnCategoryPage, "There is a promotion label but shouldn't "),
                Map.entry(promotionOnCategoryPage, "There is a promotion but shouldn't "),
                Map.entry(promotionHeader, "There is a promotion header but shouldn't "),
                Map.entry(promotionHeaderInQuickView, "There is a promotion header but shouldn't in the quick view window!"),
                Map.entry(promotionHeaderOnPromoPage, "There is a promotion header but shouldn't on the promotion page!"),
                Map.entry(countdown, "There is a countdown but shouldn't "),
                Map.entry(promotionProducts, "There are products but shouldn't "),
                Map.entry(buttonAddToCartAtPromotionBlock, "There is a button 'Add to cart' but shouldn't at the products in the multi block!")
        );

        String message = shouldExist
                ? presenceMessages.get(selector)
                : absenceMessages.get(selector);

        if (message == null)
            throw new IllegalArgumentException("No assert message found for selector: " + selector);

        softAssert.assertTrue(shouldExist == $(selector).exists(), message + page);
    }

    public void assertPromotionPeriod_TillTheEndOfCurrentDay() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(": ");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate,
                "Promotion period is not till the end of the current day!");
    }
}