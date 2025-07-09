package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class TmTab extends Tab {
    private InstrumentPanel instrumentPanel;

    public TmTab(String tabText) {
        super(tabText);
    }

    public InstrumentPanel getInstrumentPanel() {
        return instrumentPanel;
    }

    public void setInstrumentPanel(InstrumentPanel instrumentPanel) {
        this.setContent(instrumentPanel);
        this.instrumentPanel = instrumentPanel;
    }
}
