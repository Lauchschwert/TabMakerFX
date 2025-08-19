package xyz.lauchschwert.tabmaker.core.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.core.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.DialogService;
import xyz.lauchschwert.tabmaker.core.services.TmDialogService;
import xyz.lauchschwert.tabmaker.core.ui.builders.actions.FileActions;
import xyz.lauchschwert.tabmaker.core.ui.builders.actions.TabActions;
import xyz.lauchschwert.tabmaker.core.ui.builders.builder.MenuBarBuilder;
import xyz.lauchschwert.tabmaker.core.ui.builders.builder.TabPaneBuilder;
import xyz.lauchschwert.tabmaker.core.ui.builders.infos.MenuItemInfo;
import xyz.lauchschwert.tabmaker.core.ui.builders.infos.TabItemInfo;
import xyz.lauchschwert.tabmaker.core.ui.components.TmTab;
import xyz.lauchschwert.tabmaker.core.ui.panels.InstrumentPanel;

import java.io.File;

public class UserInterface {
    private VBox root;
    private TabPane tabPanelPane;
    private final TmDialogService dialogService;

    public UserInterface() {
        this.dialogService = new TmDialogService();

        initComponents();
    }

    private void initComponents() {
        this.root = new VBox();
        this.tabPanelPane = new TabPane();
    }

    public Scene createScene(Stage stage) {
        configureComponents(stage);
        return new Scene(this.root);
    }

    public void importPanel(InstrumentPanel instrumentPanel) {
        if (instrumentPanel == null) {
            DialogService.ShowAlert(Alert.AlertType.ERROR,
                    "Import Error",
                    "Failed to import panel",
                    "blab");
        }

        String name = dialogService.GetTabNameViaDialog();

        TabItemInfo tabItemInfo = new TabItemInfo(name, instrumentPanel);

        TabPaneBuilder tabPaneBuilder = new TabPaneBuilder(tabPanelPane.getTabs());
        tabPaneBuilder.addTab(tabPanelPane, tabItemInfo);
    }

    private void configureComponents(Stage stage) {
        final TmTab welcomeTab = new TmTab("Welcome", null);
        welcomeTab.setClosable(true);

        final FileActions fileActions = new FileActions(this);
        final TabActions tabActions = new TabActions(this);

        final MenuBar menuBar = new MenuBarBuilder()
                .addMenu("File",
                        new MenuItemInfo("Import Panel",
                                e ->
                                        importPanel(fileActions),

                                false),

                        new MenuItemInfo("Export Panel",
                                e -> exportPanel(fileActions),
                                false
                        )
                ).addMenu(
                        "Tabs",
                        new MenuItemInfo(
                                "Create new Tab",
                                e -> tabActions.createNewTab(),
                                false
                        )
                ).build();

        tabPanelPane.setSide(Side.TOP);
        tabPanelPane.getStyleClass().add("tabPane");
        tabPanelPane.getTabs().add(welcomeTab);
        tabPanelPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldTab, newTab) -> Platform.runLater(() -> {
                    // so the width don't get fucked up lol
                    double width = stage.getWidth();
                    stage.sizeToScene();
                    stage.setWidth(width);
                }));

        root.getStyleClass().add("root");
        root.getChildren().addAll(menuBar, tabPanelPane);
        TmLogger.debug("Configured Components successfully");
    }

    private void exportPanel(FileActions fileActions) {
        fileActions.exportAction(getSelectedTab());
    }

    private void importPanel(FileActions fileActions) {
        try {
            final File targetFile = DialogService.OpenFileDialog();
            fileActions.importAction(targetFile);
        } catch (ImportException ex) {
            TmLogger.warn("Could not import file: " + ex.getMessage());
        }
    }

    private MenuItem createMenuItem(String name, EventHandler<ActionEvent> e) {
        MenuItem importFileItem = new MenuItem(name);

        importFileItem.setOnAction(e);
        return importFileItem;
    }

    public void createNewTab(InstrumentPanel instrumentPanel) {
        String input = dialogService.GetTabNameViaDialog();
        if (input == null || input.isBlank()) {
            input = "Default";
        }

        final TmTab newTab = new TmTab(input, instrumentPanel);
        tabPanelPane.getTabs().add(newTab);
        TmLogger.debug("New Tab created successfully. Tab: " + newTab.getText());
    }

    private TmTab getSelectedTab() {
        return (TmTab) tabPanelPane.getSelectionModel().getSelectedItem();
    }

    public TmDialogService getDialogService() {
        return dialogService;
    }
}
