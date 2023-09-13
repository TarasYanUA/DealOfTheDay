package storefront;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class StPromotions {
    public StPromotions(){super();}

    public SelenideElement block_DealOfTheDay = $(".ab__deal_of_the_day");
    public SelenideElement button_AllPromotions = $(".ty-btn__text");
    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'Купите фотоаппарат')]");
    public SelenideElement chooseAnyProduct = $(".ut2-gl__item");
    public SelenideElement breadcrumb_Computers = $("a[href$='/kompyutery/'].ty-breadcrumbs__a");

    public SelenideElement button_QuickView = $(".ut2-icon-baseline-visibility");
    public SelenideElement button_ClosePopupWindow = $(".ui-icon-closethick");

    //Проверки
    public ElementsCollection labelOnCategoryPage = $$(".ab_dotd_product_label");
    public ElementsCollection promotionOnCategoryPage = $$(".ab-dotd-category-promo");
    public SelenideElement promotionHeader = $(".ui-dialog-titlebar");
}
