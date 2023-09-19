import adminPanel.AddonSettings;
import adminPanel.CsCartSettings;
import adminPanel.PromotionSettings;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import storefront.StPromotions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;

/*
В данном тест-кейсе используются значения по умолчанию:
* Обратный отсчёт до -- Окончания промо-акции
* Тип счётчика --       FlipClock
* Максимальная высота описания --   400
* Промо-акций на страницу --        4
* Выделение промо-акции --          1
* Количество отображаемых промо-акций в списках товаров --  2
* Показ истекших промо-акций --     нет
* Показ ожидаемых промо-акций --    нет
*/

public class GeneralSettings_Var2 extends TestRunner {
    @Test(priority = 1)
    public void setConfiguration_GeneralSettings_Var2(){
        //Задаём настройки модуля
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToAddonsPage();
        AddonSettings addonSettings = csCartSettings.navigateToAddonSettings();
        addonSettings.setting_CountdownTo.selectOptionByValue("end_of_the_promotion");
        addonSettings.setting_CountdownType.selectOptionByValue("flipclock");
        addonSettings.clickAndType_setting_MaximumHeightOfDescription("400");
        addonSettings.clickAndType_setting_PromotionsPerPage("4");
        addonSettings.setting_HighlightingThePromotion.selectOptionByValue("1");
        addonSettings.setting_AmountOfDisplayedPromotionsInProductLists.selectOptionByValue("2");
        if(addonSettings.setting_ShowExpiredPromotions.isSelected()){
            addonSettings.setting_ShowExpiredPromotions.click(); }
        if(addonSettings.setting_ShowAwaitingPromotions.isSelected()){
            addonSettings.setting_ShowAwaitingPromotions.click(); }
        addonSettings.button_SaveSettings.click();

        //Задаём настройки на странице промо-акции
        PromotionSettings promotionSettings = csCartSettings.navigateToPromotionSettings();
        promotionSettings.chooseRussianLanguage();
        promotionSettings.promotion_BuyCamera.click();
        promotionSettings.clickAndType_field_DetailedDescription(); //Чтобы проверить настройку "Максимальная высота описания"
        if(!promotionSettings.setting_UseAvailablePeriod.isSelected()) {
            promotionSettings.setting_UseAvailablePeriod.click();   }
        //Устанавливаем сегодняшнюю дату для поля "Доступна до", чтобы проверить настройку "Обратный отсчёт до"
        clearBothFieldsAvailable();
        promotionSettings.setting_UseAvailablePeriod.click();
        promotionSettings.setDateOfTodayForSetting_AvailableTill();
        csCartSettings.button_Save.click();
    }
    public void clearBothFieldsAvailable(){
        PromotionSettings promotionSettings = new PromotionSettings();
        promotionSettings.setting_AvailableFrom.click();
        promotionSettings.setting_AvailableFrom.clear();
        promotionSettings.setting_AvailableTill.click();
        promotionSettings.setting_AvailableTill.clear();
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Save.click();
    }

    @Test(priority = 2, dependsOnMethods = "setConfiguration_GeneralSettings_Var2")
    public void check_GeneralSettings_Var2(){
        //Переходим на главную страницу и проверяем блок "Товар дня"
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.button_Storefront.click();
        shiftBrowserTab(1);
        $(".cm-btn-success").click();
        StPromotions stPromotions = new StPromotions();
        stPromotions.block_DealOfTheDay.hover();
        SoftAssert softAssert = new SoftAssert();
        //Проверяем, что в блоке присутствует заголовок
        softAssert.assertTrue($(".pd-promotion__title").exists(),
                "There is no title of the promotion in the block!");
        //Проверяем, что в блоке присутствует описание
        softAssert.assertTrue($(".promotion-descr").exists(),
                "There is no description of the promotion in the block!");
        //Проверяем, что присутствует FlipClock счётчик в блоке
        softAssert.assertTrue($(".flip-clock-wrapper").exists(),
                "Countdown type is not FlipClock in the block!");
        //Проверяем, что в блоке присутствует кнопка "Подробнее"
        softAssert.assertTrue(stPromotions.blockButton_More.exists(),
                "There is no button 'More' in the block!");
        //Проверяем, что в блоке присутствует кнопка "Все промо-акции"
        softAssert.assertTrue(stPromotions.blockButton_AllPromotions.exists(),
                "There is no button 'All promotions' in the block!");
        makePause();
        screenshot("200 GeneralSettings_Var2 - Block 'DealOfTheDay'");
        selectLanguage_RTL();
        stPromotions.block_DealOfTheDay.hover();
        screenshot("202 GeneralSettings_Var2 - Block 'DealOfTheDay' (RTL)");

        //Переходим на страницу списка промо-акций
        stPromotions.blockButton_AllPromotions.click();
        selectLanguage_RU();
        //Проверяем, что у промо-акции присутствует текст "Только сегодня" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.text_OnlyToday.exists(),
                "There is no text 'Only today' at promotion on the promotion list page!");
        //Проверяем, что присутствует "Выделение промо-акции" (у промо-акции "Купите фотоаппарат")
        softAssert.assertTrue(stPromotions.highlight.exists(),
                "There is no Highlighting of the promotion on the promotion list page!");
        //Проверяем, что "Промо-акций на страницу" присутствует 4 на странице списка промо-акций
        softAssert.assertTrue(stPromotions.promotionsPerPage.size() == 4,
                "Promotions per page are not 4 on the promotion list page!");
        //Проверяем, что присутствует разбиение на страницы (пагинатор) на странице списка промо-акций
        softAssert.assertTrue($("#ut2_pagination_block_bottom").exists(),
                "There is no pagination on the promotion list page!");
        screenshot("205 GeneralSettings_Var2 - Page 'All promotions'");
        selectLanguage_RTL();
        screenshot("207 GeneralSettings_Var2 - Page 'All promotions' (RTL)");

        //Переходим на страницу промо-акции "Купите фотоаппарат"
        selectLanguage_RU();
        stPromotions.promotion_BuyCamera.click();
        //Проверяем, что шапка промо-акции присутствует на странице конкретной промо-акции
        softAssert.assertTrue(stPromotions.promotionHeaderOnPromoPage.exists(),
                "There is no promotion header on the promotion page!");
        //Проверяем, что присутствует FlipClock счётчик на странице промо-акции
        softAssert.assertTrue(stPromotions.flipClock.exists(),
                "Countdown type is not FlipClock on the promotion page!");
        //Проверяем, что период проведения промо-акции -- до конца текущего дня - настройка промо-акции "Доступна до"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyy"));
        String promotionDate = $(".ab__dotd_promotion_date p").getText();
        String[] splitPromotionDate = promotionDate.split(": ");
        String resultPromotionDate = splitPromotionDate[1];
        softAssert.assertEquals(resultPromotionDate, "по " + currentDate,
                "Promotion period is not till the end of the current day!");
        //Проверяем, что в промо-акции присутствуют товары
        softAssert.assertTrue(stPromotions.promotionProducts.size() >= 1,
                "There are no products on the promotion page!");
        makePause();
        screenshot("210 GeneralSettings_Var2 - Promotion page");
        selectLanguage_RTL();
        screenshot("212 GeneralSettings_Var2 - Promotion page (RTL)");
        softAssert.assertAll();
    }
}