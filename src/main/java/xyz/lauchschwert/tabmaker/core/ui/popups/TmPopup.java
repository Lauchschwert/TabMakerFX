package xyz.lauchschwert.tabmaker.core.ui.popups;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.enums.StringConstants;

import java.util.function.Consumer;

public interface TmPopup {
    void setOnButtonClick(Consumer<String> onButtonClick);

    void show(Button source);

    static ButtonGridPopup create(Consumer<String> onButtonClick, int buttonsPerRow) {
        ButtonGridPopup gridPopup = new ButtonGridPopup(buttonsPerRow);
        gridPopup.setOnButtonClick(onButtonClick);
        gridPopup.buildButtons(StringConstants.values());
        return gridPopup;
    }
}
