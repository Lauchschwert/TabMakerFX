package xyz.lauchschwert.tabmaker.ui.tabs;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class TmTab extends Tab {
    private VBox contentPanel;
    private List<TabPanel> tabPanels;

    public TmTab(String tabText) {
        super(tabText);

        this.contentPanel = new VBox();
        tabPanels = new ArrayList<>();
    }

    public void addTabPanel(TabPanel tabPanel) {
        this.contentPanel.getChildren().add(tabPanel);
        this.tabPanels.add(tabPanel);
    }

    public void removeTabPanel(TabPanel tabPanel) {
        this.contentPanel.getChildren().remove(tabPanel);
        this.tabPanels.remove(tabPanel);
    }

    public List<TabPanel> getTabPanels() {
        return tabPanels;
    }
}
