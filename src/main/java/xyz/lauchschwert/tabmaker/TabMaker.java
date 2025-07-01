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
import xyz.lauchschwert.tabmaker.handler.ImportExportHandler;
import xyz.lauchschwert.tabmaker.ui.builder.tabpanel.InstrumentPanelBuilder;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private MenuBar menuBar;
    private TabPane tabPanelPane;
    private TmTab welcomeTab;

    private static Stage stage;

    private static ImportExportHandler ImportExportHandler;

    @Override
    public void start(Stage stage) {
        TabMaker.stage = stage;

        initComponents();
        configureComponents();

        setScene(stage);
    }

    private void setScene(Stage stage) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabmakerFX");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.show();
    }

    private void configureComponents() {
        welcomeTab.setClosable(true);

        Menu fileMenu = new Menu("File");
        MenuItem newFileItem = new MenuItem("Add Panel");
        final MenuItem importFileItem = createMenuItem("Import Files", e -> {
            ImportExportHandler.handleImport();
        });

        MenuItem exportFileItem = new MenuItem("Export tabs");
        fileMenu.getItems().addAll(newFileItem, importFileItem, exportFileItem);

        Menu panelMenu = new Menu("Tabs");
        MenuItem addPanelItem = new MenuItem("Add Panel");
        addPanelItem.setOnAction(e -> {
            InstrumentPanelBuilder tpb = new InstrumentPanelBuilder(this);
            tpb.showAndWait();
            boolean success = tpb.getResult();
        });
        panelMenu.getItems().addAll(addPanelItem);

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
    }

    private MenuItem createMenuItem(String name, EventHandler<ActionEvent> e) {
        MenuItem importFileItem = new MenuItem(name);

        importFileItem.setOnAction(e);
        return importFileItem;
    }

    private void initComponents() {
        ImportExportHandler = new ImportExportHandler(this);

        root = new VBox();
        menuBar = new MenuBar();

        tabPanelPane = new TabPane();
        welcomeTab = new TmTab("Welcome");
    }

    public void createNewTab(String selectedTabName, InstrumentPanel instrumentPanel) {
        final TmTab newTab = new TmTab(selectedTabName);
        tabPanelPane.getTabs().add(newTab);
        newTab.setContent(instrumentPanel);
        ImportExportHandler.handleExport(instrumentPanel);
    }

    public static File GetFileViaFileChooser(FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose an import file.");

        fc.getExtensionFilters().addAll(
                filters
        );

        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        return fc.showOpenDialog(stage);
    }

    public static void SaveFileViaFileChooser(String content, FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");

        // Add extension filters
        fc.getExtensionFilters().addAll(filters);

        // Set initial directory
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fc.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(content);

                System.out.println("File saved to: " + file.getAbsolutePath());

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setContentText("Failed to save file: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}