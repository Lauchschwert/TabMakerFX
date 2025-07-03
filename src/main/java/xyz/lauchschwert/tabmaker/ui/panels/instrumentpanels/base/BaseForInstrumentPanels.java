package xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base;

import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

public interface BaseForInstrumentPanels {
    void addTabPanel(int index);
    TabPanel createTabPanel(int index);
}
