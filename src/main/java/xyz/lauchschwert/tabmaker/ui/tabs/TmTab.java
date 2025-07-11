package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

public class TmTab extends Tab {
    private InstrumentPanel instrumentPanel;

    public TmTab(String tabText, InstrumentPanel instrumentPanel) {
        super(tabText);
        setContent(instrumentPanel);
    }

    public InstrumentPanel getInstrumentPanel() {
        return instrumentPanel;
    }

    public void setInstrumentPanel(InstrumentPanel instrumentPanel) {
        this.setContent(instrumentPanel);
        this.instrumentPanel = instrumentPanel;
    }
}
