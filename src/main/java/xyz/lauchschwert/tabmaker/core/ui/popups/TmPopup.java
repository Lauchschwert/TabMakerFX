package xyz.lauchschwert.tabmaker.core.ui.popups;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.function.Consumer;

public class TmPopup implements Popup {
  private final Consumer<String> onButtonClick;
  private javafx.stage.Popup popup;
  private VBox container;

  public TmPopup(Consumer<String> onButtonClick, String[] buttonNames, int buttonsPerRow) {
    this.onButtonClick = onButtonClick;
    this.popup = new javafx.stage.Popup();
    this.popup.setAutoHide(true);

    initContents(buttonNames, buttonsPerRow);
  }

  @Override
  public void initContents(String[] buttonNames, int buttonsPerRow) {
    this.container = new VBox();
    container.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/Popup.css")).toExternalForm());
    container.getStyleClass().add("button-grid-popup");
    container.setPadding(new Insets(10));
    container.setSpacing(5);

    initButtons(buttonNames, buttonsPerRow, container);

    this.popup.getContent().add(container);
  }

  private void initButtons(String[] buttonNames, int buttonsPerRow, VBox container) {
    for (int i = 0; i < buttonNames.length; i += buttonsPerRow) {
      final HBox btnRow = new HBox();
      btnRow.setSpacing(5);

      int endIndex = Math.min(i + buttonsPerRow, buttonNames.length);

      for (int j = i; j < endIndex; j++) {
        final Button button = new Button(buttonNames[j]);
        button.getStyleClass().add("popup-button");
        button.setOnAction(e -> {
          onButtonClick.accept(button.getText());
          dispose();
        });
        btnRow.getChildren().add(button);
      }
      container.getChildren().add(btnRow);
    }
  }

  @Override
  public void show(Node owner, double xPos, double yPos) {
    Platform.runLater(() -> popup.show(owner, xPos, yPos));
  }

  @Override
  public void dispose() {
    if (popup != null) {
      popup.hide();
      popup.getContent().clear();
      popup = null;
    }
  }
}
