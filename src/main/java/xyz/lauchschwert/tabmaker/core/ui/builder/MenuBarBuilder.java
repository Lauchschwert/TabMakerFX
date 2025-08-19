package xyz.lauchschwert.tabmaker.core.ui.builder;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import xyz.lauchschwert.tabmaker.core.configs.MenuItemConfig;

public class MenuBarBuilder {
    private final MenuBar menuBar;

    public MenuBarBuilder() {
        this.menuBar = new MenuBar();
    }

    public MenuBar build() {
        return menuBar;
    }

    public MenuBarBuilder addSubMenu(Menu menu) {
        this.menuBar.getMenus().add(menu);
        return this;
    }

    public MenuBarBuilder addMenu(String name, MenuItemConfig subMenuInfo) {
        final MenuItem newItem = new MenuItem(subMenuInfo.name());
        newItem.setOnAction(subMenuInfo.action());
        newItem.setDisable(subMenuInfo.disable());

        final Menu newMenu = new Menu(name);
        newMenu.getItems().add(newItem);

        this.menuBar.getMenus().add(newMenu);

        return this;
    }

    public MenuBarBuilder addMenu(String name, MenuItemConfig... subMenuInfos) {
        final Menu newMenu = new Menu(name);
        for (MenuItemConfig menuItemConfig : subMenuInfos) {
            final MenuItem newItem = new MenuItem(menuItemConfig.name());
            newItem.setOnAction(menuItemConfig.action());
            newItem.setDisable(menuItemConfig.disable());
            newMenu.getItems().add(newItem);
        }
        this.menuBar.getMenus().add(newMenu);

        return this;
    }
}
