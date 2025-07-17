package xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.enums.InstrumentType;
import xyz.lauchschwert.tabmaker.enums.StringConstants;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InstrumentPanel extends VBox implements BaseForInstrumentPanels {
    private static final String DEFAULT_STRING = "E";

    protected final List<TabPanel> tabPanels;
    protected InstrumentType instrumentType;

    public InstrumentPanel(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
        tabPanels = new ArrayList<>();
        createTabPanels();
    }

    public InstrumentPanel(InstrumentType instrumentType, List<TabPanel> importPanels) {
        this.instrumentType = Objects.requireNonNull(instrumentType);
        this.tabPanels = new ArrayList<>();

        if (importPanels != null) {
            tabPanels.addAll(importPanels);
            for (TabPanel tabPanel : this.tabPanels) {
                final ScrollPane scrollPane = createScrollPane(tabPanel);
                this.getChildren().add(scrollPane);
            }
        }
    }

    private void createTabPanels() {
        for (StringConstants string : this.instrumentType.getTuning()) {
            TabPanel tabPanel = createTabPanel(string.getNote());

            // Wrap in ScrollPane for scrolling if buttons extend bounds
            final ScrollPane scrollPane = createScrollPane(tabPanel);
            this.getChildren().add(scrollPane);
            this.tabPanels.add(tabPanel);
        }
    }

    public TabPanel createTabPanel(int index) {
        String stringVar = getStringVar(index);
        if (stringVar == null) {
            stringVar = DEFAULT_STRING;
        }
        return new TabPanel(stringVar);
    }

    public TabPanel createTabPanel(String string) {
        return new TabPanel(string);
    }

    private static String getStringVar(int index) {
        if (index < 0 || index >= TabMaker.STRINGS.size()) {
            TmLogger.warn("Index out of bounds while generating a string: " + index);
            return null;
        }

        // Reset high E (index 5) back to low E (index 0) to avoid duplicate E strings
        final int offsetRange = 5;
        if (index == offsetRange) {
            index = 0;
        }
        return TabMaker.STRINGS.get(index);
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

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }
}
