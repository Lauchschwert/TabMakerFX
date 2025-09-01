package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.scene.control.Button;

public class FeatureButton extends Button {
    public FeatureButton() {
        this.getStyleClass().add("feature-button");

        this.setOnAction(e -> System.out.println("Feature Button clicked"));
    }
}
