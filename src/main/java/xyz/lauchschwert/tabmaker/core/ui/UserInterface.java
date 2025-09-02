package xyz.lauchschwert.tabmaker.core.ui;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.core.actions.FileActions;
import xyz.lauchschwert.tabmaker.core.actions.TabActions;
import xyz.lauchschwert.tabmaker.core.configs.MenuItemConfig;
import xyz.lauchschwert.tabmaker.core.configs.TabItemConfig;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.dialog.DialogService;
import xyz.lauchschwert.tabmaker.core.services.dialog.TmDialogService;
import xyz.lauchschwert.tabmaker.core.ui.builder.MenuBarBuilder;
import xyz.lauchschwert.tabmaker.core.ui.builder.TabPaneBuilder;
import xyz.lauchschwert.tabmaker.core.ui.components.buttons.TmTab;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;

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
        name = name == null ? DialogService.DEFAULT_TABNAME : name;

        final TabItemConfig tabItemConfig = new TabItemConfig(name, instrumentPanel);

        final TabPaneBuilder tabPaneBuilder = new TabPaneBuilder(tabPanelPane.getTabs());
        tabPaneBuilder.addTab(tabPanelPane, tabItemConfig);
    }

    private void configureComponents(Stage stage) {
        final TmTab welcomeTab = new TmTab("Welcome", null);
        welcomeTab.setClosable(true);

        final FileActions fileActions = new FileActions(this);
        final TabActions tabActions = new TabActions(this);

        final MenuBar menuBar = new MenuBarBuilder()
                .addMenu("File",
                        new MenuItemConfig("Import Panel",
                                e ->
                                        importPanel(fileActions),

                                false),

                        new MenuItemConfig("Export Panel",
                                e -> exportPanel(fileActions),
                                false
                        )
                ).addMenu(
                        "Tabs",
                        new MenuItemConfig(
                                "Create new Tab",
                                e -> tabActions.createNewTab(),
                                false
                        )
                ).addMenu("View",
                        new MenuItemConfig("Display Feature-Bar",
                                e -> System.out.println("")
                        )
                )
                .build();

        tabPanelPane.setSide(Side.TOP);
        tabPanelPane.getStyleClass().add("tabPane");
        tabPanelPane.getTabs().add(welcomeTab);
        tabPanelPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldTab, newTab) -> Platform.runLater(() -> {
                    // so the width doesn't get fucked up lol
                    double width = stage.getWidth();
                    stage.sizeToScene();
                    stage.setWidth(width);
                }));

        root.getStyleClass().add("root-panel");
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
