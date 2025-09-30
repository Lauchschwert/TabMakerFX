package xyz.lauchschwert.tabmaker.core.ui.components.panels;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.core.enums.TuningConstants;
import xyz.lauchschwert.tabmaker.core.ui.builder.TmPopupManager;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.FeatureButton;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.NoteButton;

public class TabPanel extends HBox {
  private final HBox noteBtnPanel, featureBar;
  private String selectedString;
  private boolean noteAdded = false;

  public TabPanel(String string) {
    this.selectedString = string;

    VBox panelWrapper = new VBox();
    panelWrapper.setSpacing(10);

    setSpacing(10);
    setPadding(new Insets(15));

    final Button stringButton = new Button(selectedString);
    stringButton.getStyleClass().addAll("tm-button", "string-button");
    stringButton.setOnAction(e -> TmPopupManager.createPopup(this::setStringName, TuningConstants.GetAllTunings(), 6)
            .show(stringButton,
                    stringButton.getBoundsInLocal().getCenterX(),
                    stringButton.getBoundsInLocal().getCenterY()));

    final VBox stringButtonWrapper = new VBox();
    stringButtonWrapper.setAlignment(Pos.CENTER);
    stringButtonWrapper.getChildren().add(stringButton);

    this.noteBtnPanel = new HBox();
    noteBtnPanel.setSpacing(5);
    noteBtnPanel.getChildren().addListener((ListChangeListener<? super Node>) observable -> setNoteAdded(true));

    this.featureBar = new HBox();
    featureBar.setSpacing(5);

    final NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());
    noteBtnPanel.getChildren().add(noteButton);

    final Separator separator = new Separator(Orientation.VERTICAL);
    setHgrow(separator, Priority.ALWAYS);

    panelWrapper.getChildren().addAll(featureBar, noteBtnPanel);
    this.getChildren().addAll(stringButtonWrapper, separator, panelWrapper);
  }

  public TabPanel(String string, String[] notes, String[] features) {
    this(string);

    importNotes(notes, features);
  }

  private void importNotes(String[] notes, String[] featureSymbols) {
    if (notes.length == 0) {
      return;
    }

    // remove default button
    this.noteBtnPanel.getChildren().clear();

    // numerated for-loop for synchronized importing of the NoteButtons and FeatureButtons
    for (int i = 0; i < notes.length; i++) {
      // import the note first
      final NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());
      noteButton.setText(notes[i]);
      this.noteBtnPanel.getChildren().add(noteButton);

      if (i > featureSymbols.length) {
        addFeatureBtn("");
        continue;
      }

      // then the respective feature button
      final String featureSymbol = featureSymbols.length == 0 ? "" : featureSymbols[i];
      addFeatureBtn(featureSymbol);
    }
  }

  public void addNoteBtn() {
    final NoteButton newButton = new NoteButton(this);

    // Calculate index properly BEFORE Platform.runLater
    final int newIndex = noteBtnPanel.getChildren().size();
    newButton.setIndex(newIndex);

    Platform.runLater(() -> {
      this.noteBtnPanel.getChildren().add(newButton);
      newButton.requestFocus();
      setNoteAdded(true);
      addFeatureBtn("");
    });
  }

  public void addFeatureBtn(String text) {
    final FeatureButton newButton = new FeatureButton(text);
    this.featureBar.getChildren().add(newButton);
  }

  public int getLastNoteButtonIndex() {
    return noteBtnPanel.getChildren().size() - 1;
  }

  public String getStringName() {
    return selectedString;
  }

  public void setStringName(String string) {
    selectedString = string;
  }

  public String[] getNotes() {
    final int noteBtnPanelSize = noteBtnPanel.getChildren().size();
    final String[] noteArray = new String[noteBtnPanelSize];
    for (int i = 0; i < noteBtnPanelSize; i++) {
      noteArray[i] = ((NoteButton) noteBtnPanel.getChildren().get(i)).getText();
    }

    return noteArray;
  }

  public void setNoteAdded(boolean noteAdded) {
    this.noteAdded = noteAdded;
  }

  public boolean isNoteAdded() {
    return noteAdded;
  }


  public String[] getFeatures() {
    final int featureBtnPanelSize = featureBar.getChildren().size();

    final String[] featureArray = new String[featureBtnPanelSize];
    for (int i = 0; i < featureBtnPanelSize; i++) {
      featureArray[i] = ((FeatureButton) featureBar.getChildren().get(i)).getText();
    }

    return featureArray;
  }

  public NoteButton getLastNoteButton() {
    return (NoteButton) noteBtnPanel.getChildren().get(getLastNoteButtonIndex());
  }
}
