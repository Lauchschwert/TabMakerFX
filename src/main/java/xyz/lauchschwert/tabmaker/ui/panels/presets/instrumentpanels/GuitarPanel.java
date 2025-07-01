package xyz.lauchschwert.tabmaker.ui.panels.presets.instrumentpanels;

import xyz.lauchschwert.tabmaker.enums.PanelSizes;
import xyz.lauchschwert.tabmaker.ui.panels.presets.InstrumentPanel;

public class GuitarPanel extends InstrumentPanel {
    public GuitarPanel() {
        super();
        initPanels(PanelSizes.GUITAR.getLength());
    }
}
