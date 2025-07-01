package xyz.lauchschwert.tabmaker.ui.panels.presets.instrumentpanels;

import xyz.lauchschwert.tabmaker.enums.PanelSizes;
import xyz.lauchschwert.tabmaker.ui.panels.presets.InstrumentPanel;

public class BassPanel extends InstrumentPanel {
    public BassPanel() {
        super();

        initPanels(PanelSizes.BASS.getLength());
    }
}
