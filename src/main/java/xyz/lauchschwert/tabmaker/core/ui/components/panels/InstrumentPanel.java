package xyz.lauchschwert.tabmaker.core.ui.components.panels;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.core.enums.InstrumentType;
import xyz.lauchschwert.tabmaker.core.enums.Tunings;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InstrumentPanel extends VBox implements Serializable {
    private static final InstrumentType DEFAULT_INSTRUMENT_TYPE = InstrumentType.GUITAR;

    protected final List<TabPanel> tabPanels;
    protected InstrumentType instrumentType;

    public InstrumentPanel(InstrumentType instrumentType) {
        this.instrumentType = instrumentType == null ? DEFAULT_INSTRUMENT_TYPE : instrumentType;
        this.tabPanels = new ArrayList<>();

        createTabPanels();
    }

    public InstrumentPanel(InstrumentType instrumentType, List<TabPanel> importPanels) {
        this.instrumentType = instrumentType == null ? DEFAULT_INSTRUMENT_TYPE : instrumentType;
        this.tabPanels = new ArrayList<>();

        if (importPanels == null) {
            return; // return early
        }

        tabPanels.addAll(importPanels);
        for (TabPanel tabPanel : this.tabPanels) {
            final ScrollPane scrollPane = createScrollPane(tabPanel);
            this.getChildren().add(scrollPane);
        }
    }

    private void createTabPanels() {
        for (Tunings string : this.instrumentType.getTuning()) {
            TabPanel tabPanel = createTabPanel(string.getNote());

            // Wrap in the ScrollPane for scrolling if buttons extend bounds
            final ScrollPane scrollPane = createScrollPane(tabPanel);
            this.getChildren().add(scrollPane);
            this.tabPanels.add(tabPanel);
        }
    }

    public TabPanel createTabPanel(String string) {
        return new TabPanel(string);
    }

    private ScrollPane createScrollPane(TabPanel tabPanel) {
        ScrollPane scrollPane = new ScrollPane(tabPanel);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);

        // Add scroll listener
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (tabPanel.isNoteAdded()) {
                scrollPane.setHvalue(1.0);
                tabPanel.setNoteAdded(false);
            }
        });
        // Set to 1.0 to activate the hvalue listener
        scrollPane.setHvalue(1.0);
        return scrollPane;
    }

    public List<TabPanel> getTabPanels() {
        return tabPanels;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }
}
