package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.enums.FeatureConstants;
import xyz.lauchschwert.tabmaker.core.ui.builder.TmPopupManager;

public class FeatureButton extends Button {
  private static final double VERTICAL_OFFSET = 10;

  public FeatureButton(String text) {
    super(text);
    this.getStyleClass().addAll("tm-button", "feature-button");

    this.setOnAction(e -> this.handleFeatureClick());
  }

  private void handleFeatureClick() {
    final Bounds localScreenBounds = this.localToScreen(getBoundsInLocal());
    final double xPos = localScreenBounds.getMinX();
    final double yPos = localScreenBounds.getMinY() + VERTICAL_OFFSET;

    TmPopupManager.createPopup(this::setText, FeatureConstants.GetAllSymbols(), 6)
            .show(this,
                    xPos,
                    yPos);
  }
}
