package xyz.lauchschwert.tabmaker.ui;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.handler.ImportExportHandler;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.builder.InstrumentPanelBuilder;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

public class UserInterface {
    private final TabMaker tabMaker;
    private VBox root;
    private TabPane tabPanelPane;
    private ImportExportHandler importExportHandler;

    public UserInterface(TabMaker tabMaker) {
        this.tabMaker = tabMaker;
        initComponents();
    }

    private void initComponents() {
        this.root = new VBox();
        this.tabPanelPane = new TabPane();
    }

    public Scene createScene() {
        configureComponents();
        return new Scene(this.root);
    }

    private void configureComponents() {
        TmTab welcomeTab = new TmTab("Welcome");
        welcomeTab.setClosable(true);

        Menu fileMenu = new Menu("File");
        MenuItem newFileItem = new MenuItem("Add Panel");

        final MenuItem importFileItem = createMenuItem("Import Files", e -> {
            try {
                IeHandler.handleImport();
            } catch (ImportException ex) {
                TmLogger.error(ex.getMessage());
            }
        });

        MenuItem exportFileItem = createMenuItem("Export Files", e -> {
            IeHandler.handleExport();
        });
        fileMenu.getItems().addAll(newFileItem, importFileItem, exportFileItem);

        MenuItem addPanelItem = createMenuItem("Add Panel", e -> {
            InstrumentPanelBuilder ipb = new InstrumentPanelBuilder(this);
            TmLogger.debug("Instrument Panel Builder initialized");
            ipb.showAndWait();
            InstrumentPanel instrumentPanel = ipb.getResult();
            this.createNewTab("instrumentPanel");
            if (instrumentPanel == null) {
                TmLogger.warn("Instrument Panel Builder did not succeed in building an instrument panel.");
            } else {
                TmLogger.debug("Instrument Panel Builder succeeded in adding another panel");
            }
        });

        Menu panelMenu = new Menu("Tabs");
        panelMenu.getItems().addAll(addPanelItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, panelMenu);

        tabPanelPane.setSide(Side.TOP);
        tabPanelPane.getStyleClass().add("tabPane");
        tabPanelPane.getTabs().add(welcomeTab);
        tabPanelPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldTab, newTab) -> {
                    Platform.runLater(() -> {
                        // so the width don't get fucked up lol
                        double width = stage.getWidth();
                        stage.sizeToScene();
                        stage.setWidth(width);
                    });
                });

        root.getStyleClass().add("root");
        root.getChildren().addAll(menuBar, tabPanelPane);
        TmLogger.debug("Configured Components successfully");
    }

    public Tab createNewTab(String tabName) { // TODO: Refactor Instrument Panel parameter => Doesnt have to be here!
        TextInputDialog tabNameDialog = new TextInputDialog("Default");
        tabNameDialog.setTitle("Enter Tab name");
        tabNameDialog.setHeaderText("Please enter the Tab name of the imported panel!\n(leave empty for default)");
        tabNameDialog.showAndWait();

        String input = tabNameDialog.getResult();
        if (input == null || input.trim().isEmpty()) {
            input = tabNameDialog.getDefaultValue();
        }

        final TmTab newTab = new TmTab(input);
        tabPanelPane.getTabs().add(newTab);
        TmLogger.debug("New Tab created successfully. Tab: " + newTab.getText());
        return newTab;
    }

    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }

    public ImportExportHandler getImportExportHandler() {
        return importExportHandler;
    }

    public void setImportExportHandler(ImportExportHandler importExportHandler) {
        this.importExportHandler = importExportHandler;
    }
}
