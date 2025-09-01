package xyz.lauchschwert.tabmaker.core.ui.components.panels;

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
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.FeatureButton;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.NoteButton;
import xyz.lauchschwert.tabmaker.core.ui.popups.ButtonGridPopup;

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
        stringButton.getStyleClass().add("string-button");
        stringButton.setOnAction(e -> ButtonGridPopup.createTuningPopup(this::setStringName, 6).show(stringButton));

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

        if (featureSymbols.length == 0) {
            return;
        }

        // remove default button
        this.noteBtnPanel.getChildren().clear();

        // numerated for-loop for synchronized importing of the NoteButtons and FeatureButtons
        for (int i = 0; i < notes.length; i++) {
            // import the notes first
            final NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());
            noteButton.setText(notes[i]);
            this.noteBtnPanel.getChildren().add(noteButton);

            // then the respective feature buttons
            final FeatureButton featureButton = new FeatureButton();
            featureButton.setText(featureSymbols[i]);
            featureBar.getChildren().add(featureButton);
        }
    }

    public void addNoteBtn() {
        NoteButton newButton = new NoteButton(this, noteBtnPanel.getChildren().size());
        this.noteBtnPanel.getChildren().add(newButton);
        setNoteAdded(true);
        newButton.requestFocus();
        ButtonGridPopup.createFretPopup(newButton::setText, 6).show(newButton);
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
}
