package adminPanel;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PromotionSettings {
    public PromotionSettings(){super();}

    private SelenideElement button_Languages = $("a[id*='sw_select_'][id*='_wrap_content']");
    private SelenideElement russianLanguage = $(".popup-icons a[href$='descr_sl=ru']");
    public void chooseRussianLanguage(){
        button_Languages.click();
        russianLanguage.shouldBe(Condition.visible).click();
    }
    public SelenideElement promotion_RacingCard = $x("//a[contains(text(), 'AB: Гоночный картинг')]");
    public SelenideElement promotion_BuyCamera = $x("//a[contains(text(), 'AB: Купите фотоаппарат')]");
    public SelenideElement promotion_BuyHairDryerVALERA = $x("//a[contains(text(), 'Купи акционный фен VALERA')]");
    private SelenideElement field_DetailedDescription = $(".redactor2-box p");
    public void clickAndType_field_DetailedDescription(){
        field_DetailedDescription.click();
        field_DetailedDescription.clear();
        field_DetailedDescription.sendKeys("Перед покупкою ноутбука визначте, для яких цілей вам він потрібен. Шукаєте робочий інструмент для офісних або базових мультимедійних завдань? Вибирайте моделі з інтегрованою відеокартою, її вистачить для комфортної та швидкої роботи. Нерідко такі ноутбуки менше важать, їх система охолодження працює тихіше, а час автономної роботи значно довший. Плануєте грати на ноутбуці в сучасні ігри? Тоді краще купити модель з відеокартою починаючи від NVIDIA GeForce GTX 1050 Ti, але й вона не потягне сучасні ігри на максимальній графіці з роздільною здатністю Full HD. Якщо ж хочете грати без компромісів, зверніть увагу на відеокарти починаючи з NVIDIA GeForce GTX 1660 Ti. Ноутбуки з відеокартами серії NVIDIA GeForce MX підійдуть для ігор з натяжкою, цей тип відеокарт не дасть можливість отримати насолоду від красивої сучасної графіки. Вони стануть у пригоді тим, хто працює з графікою в додатках для дизайну або CAD системами. У будь-яких інших випадках на ноутбуки з такими відеокартами не варто звертати увагу, і це допоможе заощадити чималу суму.");
    }
    public SelenideElement setting_UseAvailablePeriod = $("input#elm_use_avail_period");
    public SelenideElement setting_AvailableFrom = $("input#elm_date_holder_from");
    public SelenideElement setting_AvailableTill = $("input#elm_date_holder_to");
    public SelenideElement calendar_ArrowPrevious = $(".ui-datepicker-prev");
    public SelenideElement calendar_ArrowNext = $(".ui-datepicker-next");
    public SelenideElement calendar_Day15 = $("a.ui-state-default[data-date='15']");
    public SelenideElement calendar_Day26 = $("a.ui-state-default[data-date='26']");
    public void setDateOfTodayForSetting_AvailableFrom(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyy"));
        setting_AvailableFrom.click();
        setting_AvailableFrom.clear();
        setting_AvailableFrom.sendKeys(date);
        setting_AvailableFrom.pressEnter();
    }
    public void setDateOfTodayForSetting_AvailableTill(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyy"));
        setting_AvailableTill.click();
        setting_AvailableTill.clear();
        setting_AvailableTill.sendKeys(date);
        setting_AvailableTill.pressEnter();
    }
}