package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class TmTab extends Tab {
    private final InstrumentPanel instrumentPanel;

    public TmTab(String tabText, InstrumentPanel instrumentPanel) {
        super(tabText);

        this.instrumentPanel = instrumentPanel;
        if (instrumentPanel == null) {
            instrumentPanel = new InstrumentPanel();
        }
        this.setContent(instrumentPanel);
    }

    public InstrumentPanel getInstrumentPanel() {
        return instrumentPanel;
    }
}
