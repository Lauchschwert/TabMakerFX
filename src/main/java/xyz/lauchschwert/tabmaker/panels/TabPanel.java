package xyz.lauchschwert.tabmaker.panels;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.buttons.NoteButton;
import xyz.lauchschwert.tabmaker.popups.ButtonGridPopup;

public class TabPanel extends HBox {
    private final HBox noteBtnPanel;

    public TabPanel(String string) {
        setSpacing(10);
        setPadding(new Insets(10));

        Button stringButton = new Button(string);
        stringButton.setPrefSize(40, 40);


        stringButton.setOnAction(e -> {
            ButtonGridPopup.create(TabMaker.strings, stringButton::setText).show(stringButton);
        });

        Separator separator = new Separator(Orientation.VERTICAL);

        noteBtnPanel = new HBox();
        noteBtnPanel.setSpacing(5);
        NoteButton noteButton = new NoteButton(this, noteBtnPanel.getChildren().size());

        noteBtnPanel.getChildren().add(noteButton);

        this.getChildren().addAll(stringButton, separator, noteBtnPanel);
    }

    private void handleNoteSelection(String s) {
    }

    public void addNoteBtn() {
        this.noteBtnPanel.getChildren().add(new NoteButton(this, noteBtnPanel.getChildren().size()));
    }

    public int lastIndexOfNotePanel() {
        return noteBtnPanel.getChildren().size() - 1;
    }
}
