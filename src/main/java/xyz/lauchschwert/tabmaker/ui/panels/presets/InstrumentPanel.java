package xyz.lauchschwert.tabmaker.ui.panels.presets;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.ui.panels.presets.base.BaseForInstrumentPanels;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class InstrumentPanel extends VBox implements BaseForInstrumentPanels {
    protected List<TabPanel> tabPanels;
    protected static boolean NOTE_ADDED;

    protected InstrumentPanel() {
        tabPanels = new ArrayList<>();
    }

    protected void initPanels(int count) {
        for (int i = 0; i < count; i++) {
            addTabPanel(i);
        }
    }

    public void addTabPanel(int index) {
        final TabPanel tabPanel = createTabPanel(index);

        // Wrap in ScrollPane for scrolling if buttons extend bounds
        ScrollPane scrollPane = new ScrollPane(tabPanel);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            // Basic filtering so you can scroll too :)
            if (NOTE_ADDED) {
                scrollPane.setHvalue(1.0);
                NOTE_ADDED = false;
            }
        });
        scrollPane.setHvalue(1.0);
        this.getChildren().add(scrollPane);
        tabPanels.add(tabPanel);
    }

    public TabPanel createTabPanel(int i) {
        int index = i;
        // if last string since E is not a duplicate
        if (i == 5) {
            index = 0;
        }
        return new TabPanel(TabMaker.STRINGS.get(index));
    }

    public List<TabPanel> getTabPanels() {
        return tabPanels;
    }
}
