package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.scene.control.Button;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.TabPanel;
import xyz.lauchschwert.tabmaker.core.ui.popups.ButtonGridPopup;

public class NoteButton extends Button {
    private final int index;
    public static final String STYLE_CLASS = "note-button";

    public NoteButton(TabPanel tabPanel, int index) {
        this.index = index;
        this.getStyleClass().add(STYLE_CLASS);

        this.setOnAction(e -> this.openNoteSelector(tabPanel)); // add note selection
    }

    private void handleNewNote(TabPanel tabPanel, String selectedNote) {
        this.setText(selectedNote);
        if (this.index == tabPanel.getLastNoteButtonIndex()) {
            tabPanel.addNoteBtn();
        }
    }

    private void openNoteSelector(TabPanel tabPanel) {
        ButtonGridPopup.createFretPopup(selectedNote -> handleNewNote(tabPanel, selectedNote), 6).show(this);
    }
}
