package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
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
import xyz.lauchschwert.tabmaker.ui.builder.tabpanel.InstrumentPanelBuilder;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TabMaker extends Application {
    public static boolean NOTE_ADDED = false;
    public static List<String> STRINGS = Arrays.asList(
            // Standard tuning
            "E", "A", "D", "G", "B",
            // All chromatic notes for alternate tunings
            "C", "C#", "D#", "F", "F#", "G#", "A#"
    );
    public static List<String> NOTES = Arrays.asList("1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24",
            "-", "X");

    private VBox root;
    private TabPane tabPanelPane;
    private static Stage stage;

    private static ImportExportHandler IeHandler;

    @Override
    public void init() throws Exception {
        // Init config
//        Configurator configurator = new Configurator();
//        Properties config = configurator.loadConfig("default.properties");
        // init logger
        TmLogger.logStartup();
    }

    @Override
    public void start(Stage stage) {
        TabMaker.stage = stage;

        initComponents();
        configureComponents();

        setScene(stage);
    }

    @Override
    public void stop() throws Exception {
        TmLogger.logShutdown();
    }

    private void setScene(Stage stage) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabMakerFX");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.show();
    }

    private void configureComponents() {
        TmTab welcomeTab = new TmTab("Welcome", null);
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
            boolean success = ipb.getResult();
            if (!success) {
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

    private MenuItem createMenuItem(String name, EventHandler<ActionEvent> e) {
        MenuItem importFileItem = new MenuItem(name);

        importFileItem.setOnAction(e);
        return importFileItem;
    }

    private void initComponents() {
        IeHandler = new ImportExportHandler(this);

        root = new VBox();

        tabPanelPane = new TabPane();
        TmLogger.debug("TabMaker default components initialized successfully");
    }

    public void createNewTab(String selectedTabName, InstrumentPanel instrumentPanel) {
        final TmTab newTab = new TmTab(selectedTabName, instrumentPanel);
        tabPanelPane.getTabs().add(newTab);
        TmLogger.debug("New Tab created successfully. Tab: " + newTab.getText());
    }

    public static File GetFileViaFileChooser(FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(ImportExportHandler.SAVE_DIRECTORY.toFile());

        return fc.showOpenDialog(stage);
    }

    public TmTab getSelectedTab() {
        return (TmTab) tabPanelPane.getSelectionModel().getSelectedItem();
    }

    public static void main(String[] args) {
        launch();
    }
}