package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.TabPanel;
import xyz.lauchschwert.tabmaker.core.ui.popups.ButtonGridPopup;

public class NoteButton extends Button {
    private final int index;

    public NoteButton(TabPanel tabPanel, int index) {
        this.index = index;
        this.getStyleClass().add("note-button");

        this.setOnAction(e -> ButtonGridPopup.create(selectedNote -> handleNewNote(tabPanel, selectedNote), 6).show(this)); // add note selection
    }

    private void handleNewNote(TabPanel tabPanel, String selectedNote) {
        this.setText(selectedNote);
        if (this.index == tabPanel.lastIndexOfNotePanel()) {
            tabPanel.addNoteBtn();
        }
    }
}
