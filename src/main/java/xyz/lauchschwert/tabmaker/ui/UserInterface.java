package xyz.lauchschwert.tabmaker.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.handler.ImportExportHandler;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.builder.InstrumentPanelBuilder;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.File;

public class UserInterface {
    private final ImportExportHandler importExportHandler;

    private VBox root;
    private TabPane tabPanelPane;

    public UserInterface() {
        this.importExportHandler = new ImportExportHandler(this);
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

    private void configureComponents(Stage stage) {
        TmTab welcomeTab = new TmTab("Welcome", null);
        welcomeTab.setClosable(true);

        Menu fileMenu = new Menu("File");
        MenuItem newFileItem = new MenuItem("Add Panel");

        final MenuItem importFileItem = createMenuItem("Import Files", e -> {
            try {
                InstrumentPanel importedPanel = importExportHandler.handleImport();
                createNewTab(importedPanel);
            } catch (ImportException ex) {
                TmLogger.error(ex.getMessage());
            }
        });

        MenuItem exportFileItem = createMenuItem("Export Files", e -> importExportHandler.handleExport());
        fileMenu.getItems().addAll(newFileItem, importFileItem, exportFileItem);

        MenuItem addPanelItem = createMenuItem("Add Panel", e -> {
            // Build a new InstrumentPanel
            InstrumentPanelBuilder ipb = new InstrumentPanelBuilder();
            ipb.showAndWait();
            InstrumentPanel instrumentPanel = ipb.getResult();

            if (instrumentPanel == null) {
                TmLogger.warn("Instrument Panel Builder did not succeed in building an instrument panel.");
            } else {
                // Create and set panel inside a new tab
                this.createNewTab(instrumentPanel);
                TmLogger.info("Instrument Panel Builder succeeded in adding another panel");
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
        String input = openTextInputDialog();
        if (input == null || input.isBlank()) {
            input = "Default";
        }

        final TmTab newTab = new TmTab(input, instrumentPanel);
        tabPanelPane.getTabs().add(newTab);
        TmLogger.debug("New Tab created successfully. Tab: " + newTab.getText());
    }

    public File getFileViaFileChooser(FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(ImportExportHandler.SAVE_PATH.toFile());

        return fc.showOpenDialog(null);
    }

    public String openTextInputDialog() {
        TextInputDialog tabNameDialog = new TextInputDialog("Default");
        tabNameDialog.setTitle("Enter Tab name");
        tabNameDialog.setHeaderText("Please enter the Tab name of the imported panel!\n(leave empty for default)");
        tabNameDialog.showAndWait();

        return tabNameDialog.getResult();
    }

    public Tab getSelectedTab() {
        return tabPanelPane.getSelectionModel().getSelectedItem();
    }

    public static void ShowAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
