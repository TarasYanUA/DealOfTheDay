package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PromotionSettings {
    public PromotionSettings(){super();}

    private SelenideElement button_Languages = $("a[id*='_wrap_content']");
    private SelenideElement russianLanguage = $("#content_top_navigation .popup-icons a[href$='descr_sl=ru']");
    public void chooseRussianLanguage(){
        button_Languages.click();
        russianLanguage.shouldBe(Condition.visible).click();
    }
    public SelenideElement promotion_RacingCard = $x("//a[contains(text(), 'AB: Гоночный картинг')]");
    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'AB: Купите фотоаппарат')]");
    public SelenideElement promotion_BuyHairDryerVALERA = $x("//a[contains(text(), 'Купи акционный фен VALERA')]");
/*    private SelenideElement field_DetailedDescription = $("body[data-id='elm_promotion_sht_descr'] p");
    public void clickAndType_field_DetailedDescription(){
        field_DetailedDescription.scrollIntoView(true);
        field_DetailedDescription.hover().click();
        field_DetailedDescription.clear();
        field_DetailedDescription.sendKeys("Перед покупкою ноутбука визначте, для яких цілей вам він потрібен. Шукаєте робочий інструмент для офісних або базових мультимедійних завдань? Вибирайте моделі з інтегрованою відеокартою, її вистачить для комфортної та швидкої роботи. Нерідко такі ноутбуки менше важать, їх система охолодження працює тихіше, а час автономної роботи значно довший. Плануєте грати на ноутбуці в сучасні ігри? Тоді краще купити модель з відеокартою починаючи від NVIDIA GeForce GTX 1050 Ti, але й вона не потягне сучасні ігри на максимальній графіці з роздільною здатністю Full HD. Якщо ж хочете грати без компромісів, зверніть увагу на відеокарти починаючи з NVIDIA GeForce GTX 1660 Ti. Ноутбуки з відеокартами серії NVIDIA GeForce MX підійдуть для ігор з натяжкою, цей тип відеокарт не дасть можливість отримати насолоду від красивої сучасної графіки. Вони стануть у пригоді тим, хто працює з графікою в додатках для дизайну або CAD системами. У будь-яких інших випадках на ноутбуки з такими відеокартами не варто звертати увагу, і це допоможе заощадити чималу суму.");
    }*/
    public SelenideElement setting_UseAvailablePeriod = $("input#elm_use_avail_period");
    public SelenideElement setting_AvailableFrom = $("input#elm_date_holder_from");
    public SelenideElement setting_AvailableTill = $("input#elm_date_holder_to");
    public SelenideElement calendar_ArrowPrevious = $(".ui-datepicker-prev");
    public SelenideElement calendar_ArrowNext = $(".ui-datepicker-next");
    public SelenideElement calendar_Day15 = $("a.ui-state-default[data-date='15']");
    public void setPastDateForSetting_AvailableFrom(){
        setting_AvailableFrom.click();
        calendar_ArrowPrevious.shouldBe(Condition.interactable).click();
        calendar_Day15.click();
        setting_AvailableFrom.pressEnter();
    }
    public void setDateOfTodayForSetting_AvailableTill(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyy"));
        setting_AvailableTill.hover().click();
        setting_AvailableTill.clear();
        setting_AvailableTill.sendKeys(date);
        setting_AvailableTill.pressEnter();
    }
    public SelenideElement setting_StopOtherRules = $(By.id("elm_promotion_stop_other_rules"));

    public SelenideElement tab_Conditions = $("a[href$='selected_section=conditions']");
    public SelenideElement button_AddProductsToCondition = $("a[data-ca-external-click-id*=\"opener_picker_objects_condition_\"]");
    public SelenideElement field_SearchProduct = $(".sidebar-field input");
    public SelenideElement checkProductToCondition = $(".cm-item.mrg-check");
    public SelenideElement button_AddAndClose = $(".cm-process-items.cm-dialog-closer");
    public SelenideElement button_AddCondition = $x("(//a[contains(@onclick, 'fn_promotion_add')])[1]");
    public SelenideElement selectCondition = $("select[name='promotion_data[conditions][conditions][1]']");
    public SelenideElement button_AddCategories = $("a[id^='opener_picker_objects_add_condition_']");

    public SelenideElement tab_ABExtPromotions = $(By.id("ab__dotd"));
    public SelenideElement check_GroupByCategory = $(By.id("elm_ab__dotd_group_by_category"));
    public SelenideElement check_UseFilterByProducts = $(By.id("elm_use_products_filter"));
    public SelenideElement check_HideProductBlock = $(By.id("elm_hide_products_block"));
    public SelenideElement check_DisplayLabelInProductLists = $(By.id("elm_show_label_in_products_lists"));
    public SelenideElement check_DisplayPromotionInProductLists = $(By.id("elm_show_in_products_lists"));
    public SelenideElement check_DisplayCountdownOnProductPage = $(By.id("elm_show_counter_on_product_page"));
    public SelenideElement check_DisplayCountdownOnPromotionPage = $(By.id("elm_show_counter_on_promotion_page"));
    public SelenideElement button_PreviewPromotion = $(".nav__actions-bar li a");
}