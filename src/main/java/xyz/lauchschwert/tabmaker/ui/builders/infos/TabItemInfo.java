package xyz.lauchschwert.tabmaker.ui.builders.infos;

import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.InstrumentPanel;

public record TabItemInfo(String name, InstrumentPanel instrumentPanel) {
    public TabItemInfo(String name) {
        this(name, null);
    }
}
