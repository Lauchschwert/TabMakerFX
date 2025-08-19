package xyz.lauchschwert.tabmaker.core.configs;

import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

public record TabItemConfig(String name, InstrumentPanel instrumentPanel) {
    public TabItemConfig(String name) {
        this(name, null);
    }
}
