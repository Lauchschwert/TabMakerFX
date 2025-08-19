package xyz.lauchschwert.tabmaker.core.ui.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import xyz.lauchschwert.tabmaker.core.configs.TabItemConfig;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.TmTab;

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

    public void addTab(TabPane tabPanelPane, TabItemConfig tabItemConfig) {
        final String tabName = tabItemConfig.name();
        final InstrumentPanel instrumentPanel = tabItemConfig.instrumentPanel();

        final TmTab newTab = new TmTab(tabName, instrumentPanel);
        tabPanelPane.getTabs().add(newTab);
    }
}
