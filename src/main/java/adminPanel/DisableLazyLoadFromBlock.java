package adminPanel;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public interface DisableLazyLoadFromBlock {
    default void disableLazyLoadFromBlock(String blockName) {
        CsCartSettings csCartSettings = new CsCartSettings();
        csCartSettings.navigateToSectionLayouts();
        csCartSettings.layout_TabHomePage.click();
        $("div[data-ca-block-name='" + blockName + "'] ~ div[class*='grid-control-menu'] div[class*='bm-action-properties']").hover().click();

        csCartSettings.popupWindow.shouldBe(Condition.visible);
        if ($("input[id^='elm_grid_abt__ut2_use_lazy_load']").isSelected())
            $("input[id^='elm_grid_abt__ut2_use_lazy_load']").click();
        $("input[name='dispatch[block_manager.grid.update]']").click();
        csCartSettings.popupWindow.shouldBe(Condition.disappear);
    }
}