package xyz.lauchschwert.tabmaker.core.ui.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import xyz.lauchschwert.tabmaker.core.configs.TabItemConfig;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.TmTab;

public class TabPaneManager {
    private final TabPane tabPane;

    public TabPaneManager() {
        this.tabPane = new TabPane();
    }

    public TabPaneManager(ObservableList<Tab> tabs) {
        this.tabPane = new TabPane();
        // .addAll()?
        tabPane.getTabs().addAll(tabs);
//        for (Tab tab : tabs) {
//            tabPane.getTabs().add(tab);
//        }
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
