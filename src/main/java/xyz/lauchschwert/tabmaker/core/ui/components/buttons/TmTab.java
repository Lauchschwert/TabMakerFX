package xyz.lauchschwert.tabmaker.core.ui.components.buttons;

import javafx.scene.control.Tab;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

public class TmTab extends Tab {
    private InstrumentPanel instrumentPanel;

    public TmTab(String tabText, InstrumentPanel instrumentPanel) {
        super(tabText);
        this.instrumentPanel = instrumentPanel;

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
