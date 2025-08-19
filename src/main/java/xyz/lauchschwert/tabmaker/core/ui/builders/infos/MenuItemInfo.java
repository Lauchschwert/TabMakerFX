package xyz.lauchschwert.tabmaker.core.ui.builders.infos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public record MenuItemInfo(String name, EventHandler<ActionEvent> action, boolean disable) {
    public MenuItemInfo(String name, EventHandler<ActionEvent> action) {
        this(name, action, true);
    }
}
