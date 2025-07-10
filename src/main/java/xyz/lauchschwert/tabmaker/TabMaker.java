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
import xyz.lauchschwert.tabmaker.handler.ConfigHandler;
import xyz.lauchschwert.tabmaker.handler.ImportExportHandler;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;
import xyz.lauchschwert.tabmaker.ui.builder.InstrumentPanelBuilder;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class TabMaker extends Application {
    private static final double DEFAULT_WIDTH = 1000;
    private static final double DEFAULT_HEIGHT = 600;

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
    private Properties applicationProps;

    private UserInterface userInterface;

    private static ImportExportHandler IeHandler;

    @Override
    public void init() throws Exception {
        TmLogger.logInitialization();
        // init code here
        ConfigHandler.getInstance().initConfigFiles(); // getInstance() also initializes the ConfigHandler
        applicationProps = ConfigHandler.getInstance().loadProperties();

        userInterface = new UserInterface(this);

        TmLogger.logStartup();
    }

    @Override
    public void start(Stage stage) {
        initComponents();
        configureComponents();

        setScene();
    }

    @Override
    public void stop() throws Exception {
        TmLogger.logShutdown();
    }

    private void setScene() {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabMakerFX");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        String heightProp = applicationProps.getProperty("ui.window.height");
        String widthProp = applicationProps.getProperty("ui.window.width");

        try {
            double height = Double.parseDouble(heightProp);
            double width = Double.parseDouble(widthProp);
            stage.setHeight(height);
            stage.setWidth(width);
        } catch (NumberFormatException e) {
            TmLogger.warn("Error setting height and width property. Falling back to default values");
            stage.setHeight(DEFAULT_HEIGHT);
            stage.setWidth(DEFAULT_WIDTH);
        }

        stage.show();
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

    public static File GetFileViaFileChooser(FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(ImportExportHandler.SAVE_PATH.toFile());

        return fc.showOpenDialog(stage);
    }

    public Tab getSelectedTab() {
        return tabPanelPane.getSelectionModel().getSelectedItem();
    }

    public static void main(String[] args) {
        launch();
    }

    public void run() {
        launch();
    }
}