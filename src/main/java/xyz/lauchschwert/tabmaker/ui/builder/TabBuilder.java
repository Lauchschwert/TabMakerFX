package xyz.lauchschwert.tabmaker.ui.builder;

import xyz.lauchschwert.tabmaker.ui.panels.TabPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.util.List;

public class TabBuilder {
    private static String NAME;
    private static boolean CLOSEABLE;
    private static List<TabPanel> TAB_PANELS;

    public static void SetName(String string) {
        NAME = string;
    }

    public static void SetCloseable(boolean bool) {
        CLOSEABLE = bool;
    }

    public static void SetTabPanels(List<TabPanel> tabPanels) {
        TAB_PANELS = tabPanels;
    }

    public static TmTab ShowBuildUi() {
        return null;
    }
}
