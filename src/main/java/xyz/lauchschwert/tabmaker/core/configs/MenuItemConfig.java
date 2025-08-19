package xyz.lauchschwert.tabmaker.core.configs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public record MenuItemConfig(String name, EventHandler<ActionEvent> action, boolean disable) {
    public MenuItemConfig(String name, EventHandler<ActionEvent> action) {
        this(name, action, true);
    }
}
