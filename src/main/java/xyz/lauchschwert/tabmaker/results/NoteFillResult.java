package xyz.lauchschwert.tabmaker.results;

import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.util.ArrayList;
import java.util.List;

public class NoteFillResult {
    private List<TabPanel> tabPanels;
    private int fillLength;
    private List<String> notes;

    public NoteFillResult() {
        this.tabPanels = new ArrayList<>();
    }

    public NoteFillResult(List<TabPanel> tabPanels, int fillLength, List<String> notes) {
        this.tabPanels = tabPanels;
        this.fillLength = fillLength;
        this.notes = notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void setFillLength(int fillLength) {
        this.fillLength = fillLength;
    }

    public void setTabPanels(List<TabPanel> tabPanels) {
        this.tabPanels = tabPanels;
    }

    public List<TabPanel> getTabPanels() {
        return tabPanels;
    }

    public int getFillLength() {
        return fillLength;
    }

    public List<String> getNotes() {
        return notes;
    }
}
