package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.ui.builder.TabBuilder;
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

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setMinWidth(800);
        root.setPrefWidth(1200);
        root.getStyleClass().add("root");

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.getStyleClass().add("tabPane");

        Tab addTab = new TmTab("+");
        addTab.setClosable(false);
        addTab.getStyleClass().add("addTab");

        Tab tab1 = new Tab("Guitar");
        tab1.setClosable(false);

        Tab tab2 = new Tab("Bass");
        tab2.setClosable(false);

        VBox guitarPanelContainer = new VBox();
        guitarPanelContainer.setSpacing(20);

        VBox bassPanelContainer = new VBox();
        bassPanelContainer.setSpacing(20);

        for (int i = 0; i < 6; i++) {
            createTabPanel(i, guitarPanelContainer);

            if (i < 4) {
                createTabPanel(i, bassPanelContainer);
            }
        }

        tab1.setContent(guitarPanelContainer);
        tab2.setContent(bassPanelContainer);

        tabPane.getTabs().addAll(tab1, tab2);

        root.getChildren().add(tabPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabmakerFX");
        stage.setScene(scene);

        Platform.runLater(() -> {
            double height = root.getHeight();
            stage.setMinHeight(height);
            // Whyever I need to add 40 here. It's for the buttons so please don't remove sadge
            stage.setMaxHeight(height + 40);
        });

        stage.show();
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