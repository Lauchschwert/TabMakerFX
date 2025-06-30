package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import xyz.lauchschwert.tabmaker.ui.panels.TabPanel;

import java.util.List;

public class TmTab extends Tab {
    private List<TabPanel> tabPanels;

    public TmTab(String tabText) {
        super(tabText);
    }

    public void addTabPanel(TabPanel tabPanel) {
        this.tabPanels.add(tabPanel);
    }

    public void removeTabPanel(TabPanel tabPanel) {
        this.tabPanels.remove(tabPanel);
    }

    public List<TabPanel> getTabPanels() {
        return tabPanels;
    }
}
