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
import xyz.lauchschwert.tabmaker.panels.TabPanel;

import java.awt.*;
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

    public static Font btnFont = new Font("sansserif", Font.PLAIN, 20);

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setMinWidth(800);
        root.getStyleClass().add("root");

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.getStyleClass().add("tabPane");

        Tab tab1 = new Tab("Guitar");
        tab1.setClosable(false);
        tab1.setStyle("-fx-font-size: 16");

        Tab tab2 = new Tab("Bass");
        tab2.setClosable(false);
        tab2.setStyle("-fx-font-size: 16");

        VBox guitarPanelContainer = new VBox();
        guitarPanelContainer.setSpacing(20);

        VBox bassPanelContainer = new VBox();
        bassPanelContainer.setSpacing(20);

        for (int i = 0; i < 6; i++) {
            final TabPanel guitarTabPanel = createTabPanel(i);

            // Wrap in ScrollPane for guitar
            ScrollPane guitarScrollPane = new ScrollPane(guitarTabPanel);
            guitarScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            guitarScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            guitarScrollPane.setFitToHeight(true);
            guitarScrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
                // Basic filtering so you can scroll too :)
                if (NOTE_ADDED) {
                    guitarScrollPane.setHvalue(1.0);
                    NOTE_ADDED = false;
                }
            });
            guitarPanelContainer.getChildren().add(guitarScrollPane);
            guitarScrollPane.setHvalue(1.0);

            if (i < 4) {
                final TabPanel bassTabPanel = createTabPanel(i);

                // Wrap in ScrollPane for bass
                ScrollPane bassScrollPane = new ScrollPane(bassTabPanel);
                bassScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                bassScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                bassScrollPane.setFitToHeight(true);
                bassScrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
                    bassScrollPane.setHvalue(1.0);
                });
                bassPanelContainer.getChildren().add(bassScrollPane);

                bassScrollPane.setHvalue(1.0);
            }
        }

        tab1.setContent(guitarPanelContainer);
        tab2.setContent(bassPanelContainer);

        tabPane.getTabs().

                addAll(tab1, tab2);

        root.getChildren().

                addAll(tabPane);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabmakerFX");
        stage.setScene(scene);

        Platform.runLater(() -> {
            double height = root.getHeight();
            stage.setMaxHeight(height);
            stage.setMinHeight(height);
        });

        stage.show();
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