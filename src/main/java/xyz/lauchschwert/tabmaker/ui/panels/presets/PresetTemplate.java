package xyz.lauchschwert.tabmaker.ui.panels.presets;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.List;

import static java.util.List.of;

public abstract class PresetTemplate extends VBox implements BaseForPresets
{
    protected final List<TabPanel> tabPanels;

    protected PresetTemplate() {
        tabPanels = of();
    }
}
