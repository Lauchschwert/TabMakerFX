package xyz.lauchschwert.tabmaker.core.ui.popups;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.enums.Tunings;

import java.util.function.Consumer;

public interface TmPopup {
    void setOnButtonClick(Consumer<String> onButtonClick);

    void show(Button source);
}
