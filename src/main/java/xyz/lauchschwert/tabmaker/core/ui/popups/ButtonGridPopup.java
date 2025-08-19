package xyz.lauchschwert.tabmaker.core.ui.popups;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import xyz.lauchschwert.tabmaker.core.enums.Tunings;

import java.util.Objects;
import java.util.function.Consumer;

public class ButtonGridPopup implements TmPopup {

    private final Popup popup;
    private final VBox container;
    private final int buttonsPerRow;
    private Consumer<String> onButtonClick;

    public ButtonGridPopup(int buttonsPerRow) {
        this.buttonsPerRow = buttonsPerRow;
        this.popup = new Popup();
        this.popup.setAutoHide(true);

        this.container = new VBox(5);
        this.container.getStyleClass().add("button-grid-popup");
        this.container.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/Popup.css")).toExternalForm());

        this.popup.getContent().add(container);
    }

    public static ButtonGridPopup createTuningPopup(Consumer<String> onButtonClick, int buttonsPerRow) {
        ButtonGridPopup gridPopup = new ButtonGridPopup(buttonsPerRow);
        gridPopup.setOnButtonClick(onButtonClick);

        String[] tunings = Tunings.GetAllTunings();

        gridPopup.buildButtons(tunings);
        return gridPopup;
    }

    public static ButtonGridPopup createFretPopup(Consumer<String> onButtonClick, int buttonsPerRow) {
        ButtonGridPopup gridPopup = new ButtonGridPopup(buttonsPerRow);
        gridPopup.setOnButtonClick(onButtonClick);

        String[] fretNumbers = new String[25];
        for (int i = 0; i < fretNumbers.length; i++) {
            fretNumbers[i] = String.valueOf(i);
        }

        gridPopup.buildButtons(fretNumbers);
        return gridPopup;
    }

    protected void buildButtons(String[] buttonTexts) {
        container.getChildren().clear();

        for (int i = 0; i < buttonTexts.length; i += buttonsPerRow) {
            HBox row = new HBox(4);
            row.setSpacing(10);

            for (int j = i; j < Math.min(i + buttonsPerRow, buttonTexts.length); j++) {
                Button btn = new Button(buttonTexts[j]);
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

    @Override
    public void setOnButtonClick(Consumer<String> onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    @Override
    public void show(Button sourceButton) {
        Bounds bounds = sourceButton.getBoundsInLocal();
        Point2D screenCoords = sourceButton.localToScreen(bounds.getMinX(), bounds.getMaxY());
        popup.show(sourceButton, screenCoords.getX(), screenCoords.getY());
    }
}