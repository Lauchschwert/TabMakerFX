package xyz.lauchschwert.tabmaker.popups;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ButtonGridPopup {

    private final Popup popup;
    private final VBox container;
    private final int buttonsPerRow;
    private Consumer<String> onButtonClick;

    public ButtonGridPopup() {
        this(5); // Default 5 buttons per row
    }

    public ButtonGridPopup(int buttonsPerRow) {
        this.buttonsPerRow = buttonsPerRow;
        this.popup = new Popup();
        this.popup.setAutoHide(true);

        this.container = new VBox(5);
        this.container.getStyleClass().add("button-grid-popup");
        this.container.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/Popup.css")).toExternalForm());

        this.popup.getContent().add(container);
    }

    public static ButtonGridPopup create(List<String> buttonTexts, Consumer<String> onButtonClick) {
        ButtonGridPopup gridPopup = new ButtonGridPopup();
        gridPopup.setOnButtonClick(onButtonClick);
        gridPopup.buildButtons(buttonTexts);
        return gridPopup;
    }

    public static ButtonGridPopup create(List<String> buttonTexts, Consumer<String> onButtonClick, int buttonsPerRow) {
        ButtonGridPopup gridPopup = new ButtonGridPopup(buttonsPerRow);
        gridPopup.setOnButtonClick(onButtonClick);
        gridPopup.buildButtons(buttonTexts);
        return gridPopup;
    }

    public void setOnButtonClick(Consumer<String> onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    private void buildButtons(List<String> buttonTexts) {
        container.getChildren().clear();

        for (int i = 0; i < buttonTexts.size(); i += buttonsPerRow) {
            HBox row = new HBox(4);
            row.setSpacing(10);

            for (int j = i; j < Math.min(i + buttonsPerRow, buttonTexts.size()); j++) {
                Button btn = new Button(buttonTexts.get(j));
                btn.getStyleClass().add("popup-button");
                btn.setOnAction(e -> {
                    if (onButtonClick != null) {
                        onButtonClick.accept(btn.getText());
                    }
                    popup.hide();
                });
                row.getChildren().add(btn);
            }

            container.getChildren().add(row);
        }
    }

    public void show(Button sourceButton) {
        Bounds bounds = sourceButton.getBoundsInLocal();
        Point2D screenCoords = sourceButton.localToScreen(bounds.getMinX(), bounds.getMaxY());
        popup.show(sourceButton, screenCoords.getX(), screenCoords.getY());
    }

    public void hide() {
        popup.hide();
    }

    public boolean isShowing() {
        return popup.isShowing();
    }
}