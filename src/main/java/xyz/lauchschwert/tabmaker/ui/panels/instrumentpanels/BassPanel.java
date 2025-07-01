package xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels;

import xyz.lauchschwert.tabmaker.enums.PanelSizes;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

public class BassPanel extends InstrumentPanel {
    public BassPanel() {
        super();

        initPanels(PanelSizes.BASS.getLength());
    }
}
