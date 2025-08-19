package xyz.lauchschwert.tabmaker.core.ui.components.panels;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import xyz.lauchschwert.tabmaker.core.ui.popups.ButtonGridPopup;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.NoteButton;
import xyz.lauchschwert.tabmaker.core.ui.popups.TmPopup;

public class TabPanel extends HBox {
    private final HBox noteBtnPanel;
    private String selectedString;
    private boolean noteAdded;

    public TabPanel(String string) {
        this.selectedString = string;

        setSpacing(10);
        setPadding(new Insets(15));

        final Button stringButton = new Button(string);
        stringButton.getStyleClass().add("string-button");
        stringButton.setOnAction(e -> {
            ButtonGridPopup.createTuningPopup(this::setStringName, 6).show(stringButton);
        });

        Separator separator = new Separator(Orientation.VERTICAL);

        noteBtnPanel = new HBox();
        noteBtnPanel.setSpacing(5);
        NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());
        noteBtnPanel.getChildren().add(noteButton);

        this.getChildren().addAll(stringButton, separator, noteBtnPanel);
    }

    public TabPanel(String string, String[] notes) {
        this(string);

        if (notes.length == 0) {
            return; // return early since we have no notes to import and don't want to skip the clear of noteBtnPanel
        }

        // remove default button
        this.noteBtnPanel.getChildren().clear();

        // import buttons
        for (String note : notes) {
            NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());
            noteButton.setText(note);
            this.noteBtnPanel.getChildren().add(noteButton);
        }
    }

    public void addNoteBtn() {
        NoteButton newButton = new NoteButton(this, noteBtnPanel.getChildren().size());
        this.noteBtnPanel.getChildren().add(newButton);
        setNoteAdded(true);
        newButton.requestFocus();
    }

    public int lastIndexOfNotePanel() {
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
        String[] noteArray = new String[noteBtnPanelSize];
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
}
