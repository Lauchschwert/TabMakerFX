package xyz.lauchschwert.tabmaker.ui.buttons;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;
import xyz.lauchschwert.tabmaker.ui.popups.ButtonGridPopup;

public class NoteButton extends Button {
    private final TabPanel tabPanel;
    private final int index;

    public NoteButton(TabPanel tabPanel, int index) {
        this.tabPanel = tabPanel;
        this.index = index;
        this.getStyleClass().add("note-button");

        this.setOnAction(e -> {
            ButtonGridPopup.create(TabMaker.NOTES, selectedNote -> handleNewNote(tabPanel, selectedNote), 6).show(this);
        }); // add note selection
    }

    private void handleNewNote(TabPanel tabPanel, String selectedNote) {
        this.setText(selectedNote);
        if (this.index == tabPanel.lastIndexOfNotePanel()) {
            tabPanel.addNoteBtn();
        }
    }

    public int getIndex() {
        return index;
    }
}
