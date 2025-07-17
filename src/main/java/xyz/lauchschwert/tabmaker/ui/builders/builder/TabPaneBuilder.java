package xyz.lauchschwert.tabmaker.ui.builders.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import xyz.lauchschwert.tabmaker.ui.builders.infos.TabItemInfo;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

public class TabPaneBuilder {
    private final TabPane tabPane;

    public TabPaneBuilder() {
        this.tabPane = new TabPane();
    }

    public TabPaneBuilder(ObservableList<Tab> tabs) {
        this.tabPane = new TabPane();
        for (Tab tab : tabs) {
            tabPane.getTabs().add(tab);
        }
    }

    public TabPane build() {
        return tabPane;
    }

    public void addTab(TabPane tabPanelPane, TabItemInfo tabItemInfo) {
        final String tabName = tabItemInfo.name();
        final InstrumentPanel instrumentPanel = tabItemInfo.instrumentPanel();

        final TmTab newTab = new TmTab(tabName, instrumentPanel);
        tabPanelPane.getTabs().add(newTab);
    }
}
