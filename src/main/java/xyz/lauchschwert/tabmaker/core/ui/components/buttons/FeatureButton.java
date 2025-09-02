package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.scene.control.Button;

public class FeatureButton extends Button {
    public FeatureButton(String text) {
        super(text);
        this.getStyleClass().addAll("tm-button", "feature-button");

        this.setOnAction(e -> System.out.println("Feature Button clicked"));
    }
}
