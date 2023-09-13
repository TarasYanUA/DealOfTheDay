package promotionSettings;

import generalSettings.TestRunner;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import adminPanel.CsCartSettings;

/*
Настройки на странице промо-акции:
* Задать период доступности --  выкл
* Не применять другие промо-акции --    выкл
* Скрыть блок товаров --        нет
* Отображать счётчик на странице товара --      да
*/

public class ProductPage extends TestRunner {
    @Test(priority = 2)
    public void check_ProductPage(){
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.field_SearchOnTop.click();
        csCartSettings.field_SearchOnTop.setValue("M0219A3GX3").sendKeys(Keys.ENTER);
        csCartSettings.gearWheelOnTop.click();
        csCartSettings.button_Preview.click();
        shiftBrowserTab(1);
        selectLanguage_RU();
    }

}
