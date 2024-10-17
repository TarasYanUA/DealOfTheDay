package adminPanel;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import static com.codeborne.selenide.Selenide.*;

public interface CheckMenuToBeActive {
    default void checkMenuToBeActive(String menuDispatch, SelenideElement menu) {
        checkMenu_Addons_ToBeActive();

        String selector = "a[href$='" + menuDispatch + "'].main-menu-1__link";
        ElementsCollection elements = $$(selector + " ~ a[class*='main-menu-1__toggle--active']");
        try {
            if (elements.isEmpty())
                menu.click();
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
                Selenide.sleep(1000);
            }
        } catch (ElementNotFound e) {
        }
    }
}