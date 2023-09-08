package storefront;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class StPromotions {
    public StPromotions(){super();}

    public SelenideElement block_DealOfTheDay = $(".ab__deal_of_the_day");
    public SelenideElement button_AllPromotions = $(".ty-btn__text");
    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'Купите фотоаппарат')]");
}
