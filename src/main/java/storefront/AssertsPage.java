package storefront;

import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static com.codeborne.selenide.Selenide.$;

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


    public void assertElementPresence(String selector, String page, boolean shouldExist) {
        Map<String, String> presenceMessages = Map.ofEntries(
                Map.entry(filterByProducts, "There are no product filters " + page),
                Map.entry(productBlock, "There is no product block " + page),
                Map.entry(labelOnCategoryPage, "There is no promotion label " + page),
                Map.entry(promotionOnCategoryPage, "There is no any promotion " + page),
                Map.entry(promotionHeader, "There is no promotion header " + page),
                Map.entry(promotionHeaderInQuickView, "There is no promotion header in the quick view window!"),
                Map.entry(promotionHeaderOnPromoPage, "There is no promotion header on the promotion page!"),
                Map.entry(countdown, "There is no countdown " + page),
                Map.entry(javaClock, "Countdown type is not Javascript " + page),
                Map.entry(flipClock, "Countdown type is not FlipClock " + page),
                Map.entry(highlight, "There is no Highlighting of the promotion " + page),
                Map.entry(promotionProducts, "There are no products " + page),
                Map.entry(button_MoreProductsFromCategory, "There is no any button 'More products from category' " + page),
                Map.entry(blockTitle, "There is no title of the promotion " + page),
                Map.entry(blockDescription, "There is no description of the promotion in the block!"),
                Map.entry(blockProducts, "There are no products in the block!")
                );

        Map<String, String> absenceMessages = Map.ofEntries(
                Map.entry(filterByProducts, "There are product filters but shouldn't " + page),
                Map.entry(productBlock, "There is a product block but shouldn't " + page),
                Map.entry(labelOnCategoryPage, "There is a promotion label but shouldn't " + page),
                Map.entry(promotionOnCategoryPage, "There is a promotion but shouldn't " + page),
                Map.entry(promotionHeader, "There is a promotion header but shouldn't " + page),
                Map.entry(promotionHeaderInQuickView, "There is a promotion header but shouldn't in the quick view window!"),
                Map.entry(promotionHeaderOnPromoPage, "There is a promotion header but shouldn't on the promotion page!"),
                Map.entry(countdown, "There is a countdown but shouldn't " + page),
                Map.entry(promotionProducts, "There are products but shouldn't " + page)
        );

        String message = shouldExist
                ? presenceMessages.get(selector)
                : absenceMessages.get(selector);

        if (message == null)
            throw new IllegalArgumentException("No assert message found for selector: " + selector);

        softAssert.assertTrue(shouldExist == $(selector).exists(), message);
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