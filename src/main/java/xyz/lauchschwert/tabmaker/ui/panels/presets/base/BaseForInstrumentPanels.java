package xyz.lauchschwert.tabmaker.ui.panels.presets.base;

import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

public interface BaseForInstrumentPanels {
    void addTabPanel(int index);
    TabPanel createTabPanel(int index);
}
