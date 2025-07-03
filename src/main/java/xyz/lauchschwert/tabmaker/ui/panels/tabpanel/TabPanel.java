package xyz.lauchschwert.tabmaker.ui.panels.tabpanel;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.ui.buttons.NoteButton;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.popups.ButtonGridPopup;

public class TabPanel extends HBox {
    private final HBox noteBtnPanel;
    private final Button stringButton;
    private boolean noteAdded;


    public TabPanel(String string) {
        setSpacing(10);
        setPadding(new Insets(15));

        stringButton = new Button(string);
        stringButton.getStyleClass().add("string-button");

        stringButton.setOnAction(e -> {
            ButtonGridPopup.create(TabMaker.STRINGS, stringButton::setText, 6).show(stringButton);
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
        return stringButton.getText();
    }

    public String[] getNotes() {
        final int noteBtnPanelSize = noteBtnPanel.getChildren().size();
        String[] noteArray = new String[noteBtnPanelSize];
        for (int i = 0; i < noteBtnPanelSize; i++) {
            noteArray[i] = ((NoteButton) noteBtnPanel.getChildren().get(i)).getText();
        }

        return noteArray;
    }

    public boolean isNoteAdded() {
        return noteAdded;
    }

    public void setNoteAdded(boolean noteAdded) {
        this.noteAdded = noteAdded;
    }
}
