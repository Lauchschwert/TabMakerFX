package xyz.lauchschwert.tabmaker.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.handler.ImportExportService;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.builders.actions.menuitems.FileActions;
import xyz.lauchschwert.tabmaker.ui.builders.actions.tabitems.TabActions;
import xyz.lauchschwert.tabmaker.ui.builders.builder.MenuBarBuilder;
import xyz.lauchschwert.tabmaker.ui.builders.builder.TabPaneBuilder;
import xyz.lauchschwert.tabmaker.ui.builders.infos.MenuItemInfo;
import xyz.lauchschwert.tabmaker.ui.builders.infos.TabItemInfo;
import xyz.lauchschwert.tabmaker.ui.dialog.DialogService;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

public class UserInterface {
    private final ImportExportService importExportService;

    private VBox root;
    private TabPane tabPanelPane;

    public UserInterface() {
        this.importExportService = new ImportExportService();
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

        String name = DialogService.GetStringViaDialog();

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
                                e -> fileActions.importAction(),
                                false
                        ),
                        new MenuItemInfo("Export Panel",
                                e -> fileActions.exportAction(getSelectedTab().getInstrumentPanel()),
                                false
                        )
                ).addMenu(
                        "Tabs",
                        new MenuItemInfo(
                                "Add Panel",
                                e -> tabActions.addNewTab(),
                                false
                        )
                ).build();

//        Menu fileMenu = new Menu("File");
//        MenuItem newFileItem = new MenuItem("Add Panel");

//        final MenuItem importFileItem = createMenuItem("Import Files", e -> {
//            try {
//                InstrumentPanel importedPanel = importExportService.handleImport();
//                createNewTab(importedPanel);
//            } catch (ImportException ex) {
//                TmLogger.error(ex.getMessage());
//            }
//        });

//        fileMenu.getItems().addAll(newFileItem, importFileItem, exportFileItem);

//        MenuItem addPanelItem = createMenuItem("Add Panel", e -> {
//
//        });

        Menu panelMenu = new Menu("Tabs");
//        panelMenu.getItems().addAll(addPanelItem);

//        MenuBar menuBar = new MenuBar();
//        menuBar.getMenus().addAll(fileMenu, panelMenu);

//        TabPaneBuilder tabPaneBuilder = new TabPaneBuilder(tabPanelPane.getTabs());

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

    private MenuItem createMenuItem(String name, EventHandler<ActionEvent> e) {
        MenuItem importFileItem = new MenuItem(name);

        importFileItem.setOnAction(e);
        return importFileItem;
    }

    public void createNewTab(InstrumentPanel instrumentPanel) {
        String input = DialogService.GetStringViaDialog();
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

    public ImportExportService getImportExportService() {
        return importExportService;
    }
}
