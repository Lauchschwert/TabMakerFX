package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class TmTab extends Tab {
    private final InstrumentPanel instrumentPanel;

    public TmTab(String tabText, InstrumentPanel instrumentPanel) {
        super(tabText);

        this.instrumentPanel = instrumentPanel;
        if (instrumentPanel == null) {
            System.out.println("instrumentPanel is null");
            instrumentPanel = new InstrumentPanel();
        }
    }

    public List<TabPanel> getTabPanels() {
        return instrumentPanel.getTabPanels();
    }

    public InstrumentPanel getInstrumentPanel() {
        return instrumentPanel;
    }
}
