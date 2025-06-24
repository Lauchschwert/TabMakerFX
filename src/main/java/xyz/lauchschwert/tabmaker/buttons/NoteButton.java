package xyz.lauchschwert.tabmaker.buttons;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.panels.TabPanel;
import xyz.lauchschwert.tabmaker.popups.ButtonGridPopup;

public class NoteButton extends Button {
    private final TabPanel tabPanel;
    private final int index;

    public NoteButton(TabPanel tabPanel, int index) {
        this.tabPanel = tabPanel;
        this.index = index;
        this.getStyleClass().add("note-button");

        this.setPrefSize(50, 50);
        this.setOnAction(e -> {
            ButtonGridPopup.create(TabMaker.notes, selectedNote -> handleNewNote(tabPanel, selectedNote), 6).show(this);
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
