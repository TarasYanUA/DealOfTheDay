package adminPanel;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.*;

public interface CheckMenuToBeActive {
    default void checkMenuToBeActive(WebElement menu) {
        checkMenu_Addons_ToBeActive();
        try {
            ElementsCollection elements = $$x(menu + "/../..//a[contains(@class, 'active')]");
            // Проверяем, пустой ли список элементов
            if (elements.isEmpty()) {
                menu.click();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
        }
    }

    default void checkMenu_Products_ToBeActive() {
        checkMenu_Addons_ToBeActive();
        try {
            ElementsCollection elements = $$("a[href$='products.manage'].main-menu-1__link ~ a[class*='main-menu-1__toggle--active']");
            if (elements.isEmpty())
                $("a[href$='products.manage'].main-menu-1__link").click();

        } catch (ElementNotFound e) {
        }
    }


    static void checkMenu_Addons_ToBeActive() {
        // Проверяем наличие хотя бы одного из элементов
        ElementsCollection firstElement = $$x("//span[text()='_ab__addons']/../../..//div[contains(@class, 'in collapse')]");
        ElementsCollection secondElement = $$x("//span[text()='_ab__addons']/../..//a[contains(@class, 'main-menu-1__toggle--active')]");

        try {
        if (!firstElement.isEmpty() || !secondElement.isEmpty()) {
            // Если хотя бы один элемент присутствует, выполняем действие
            $x("//span[text()='_ab__addons']").click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }} catch (ElementNotFound e) {
        }
    }
}