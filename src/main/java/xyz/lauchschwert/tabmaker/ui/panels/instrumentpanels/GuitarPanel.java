package xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels;

import xyz.lauchschwert.tabmaker.enums.PanelSizes;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class GuitarPanel extends InstrumentPanel {
    public GuitarPanel() {
        super();
        initPanels(PanelSizes.GUITAR.getLength());
    }
}
