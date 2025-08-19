package xyz.lauchschwert.tabmaker.core.ui.builders.infos;

import xyz.lauchschwert.tabmaker.core.ui.panels.InstrumentPanel;

public record TabItemInfo(String name, InstrumentPanel instrumentPanel) {
    public TabItemInfo(String name) {
        this(name, null);
    }
}
