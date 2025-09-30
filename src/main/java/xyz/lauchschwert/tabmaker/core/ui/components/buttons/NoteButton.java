package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.enums.TuningConstants;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.ui.builder.TmPopupManager;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.TabPanel;

public class NoteButton extends Button {
  private static final double VERTICAL_POPUP_OFFSET = 10;
  private int index;
  public static final String STYLE_CLASS = "note-button";

  public NoteButton(TabPanel tabPanel) {
    this.getStyleClass().addAll("tm-button", STYLE_CLASS);

    this.setOnAction(e -> this.openNoteSelector(tabPanel)); // add note selection
  }

  public NoteButton(TabPanel tabPanel, int index) {
    this(tabPanel);
    setIndex(index);
  }

  private void handleNewNote(TabPanel tabPanel, String selectedNote) {
    this.setText(selectedNote);
    if (this.index == tabPanel.getLastNoteButtonIndex()) {
      tabPanel.addNoteBtn();
    }
  }

  public void openNoteSelector(TabPanel tabPanel) {
    final Bounds localScreenBounds = this.localToScreen(this.getBoundsInLocal());

    final double xPos = localScreenBounds.getCenterX();
    final double yPos = localScreenBounds.getCenterY() + VERTICAL_POPUP_OFFSET;

    TmLogger.info("Button " + this.index + " - Xpos: " + xPos + " Ypos: " + yPos);

    Platform.runLater(() -> TmPopupManager.createPopup(selectedNote -> this.handleNewNote(tabPanel, selectedNote),
                    TuningConstants.FRET_COUNT, 7)
            .show(this, xPos, yPos));
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
