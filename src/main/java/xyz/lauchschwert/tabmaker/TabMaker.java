package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;
import xyz.lauchschwert.tabmaker.ui.panels.TabPanel;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TabMaker extends Application {
    public static boolean NOTE_ADDED = false;
    public static List<String> strings = Arrays.asList(
            // Standard tuning
            "E", "A", "D", "G", "B",
            // All chromatic notes for alternate tunings
            "C", "C#", "D#", "F", "F#", "G#", "A#"
    );
    public static List<String> notes = Arrays.asList("1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24",
            "-", "X");

    private VBox root;
    private MenuBar menuBar;
    private TabPane tabPanelPane;
    private TmTab welcomeTab;

    @Override
    public void start(Stage stage) {
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
        stage.show();
    }

    private void configureComponents() {
        welcomeTab.setClosable(true);

        Menu panelMenu = new Menu("Panels");

        MenuItem addPanelItem = new MenuItem("Add Panel");
        addPanelItem.setOnAction(e -> {
//            createTabPanel();
        });

        panelMenu.getItems().addAll(addPanelItem);

        menuBar.getMenus().addAll(panelMenu);

        tabPanelPane.setSide(Side.TOP);
        tabPanelPane.getStyleClass().add("tabPane");
        tabPanelPane.getTabs().add(welcomeTab);

        root.setMinWidth(800);
        root.setPrefWidth(1200);
        root.getStyleClass().add("root");
        root.getChildren().addAll(menuBar, tabPanelPane);
    }

    private void initComponents() {
        root = new VBox();
        menuBar = new MenuBar();

        tabPanelPane = new TabPane();
        welcomeTab = new TmTab("Welcome");
    }

    private static void createTabPanel(int i, VBox container) {
        final TabPanel tabPanel = createTabPanel(i);

        // Wrap in ScrollPane for scrolling if buttons extend bounds
        ScrollPane scrollPane = new ScrollPane(tabPanel);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            // Basic filtering so you can scroll too :)
            if (NOTE_ADDED) {
                scrollPane.setHvalue(1.0);
                NOTE_ADDED = false;
            }
        });
        scrollPane.setHvalue(1.0);
        container.getChildren().add(scrollPane);
    }

    private static TabPanel createTabPanel(int i) {
        int index = i;
        // if last string since E is not a duplicate
        if (i == 5) {
            index = 0;
        }
        return new TabPanel(strings.get(index));
    }

    public static void main(String[] args) {
        launch();
    }
}