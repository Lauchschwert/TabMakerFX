package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.panels.TabPanel;

import java.util.Arrays;
import java.util.List;

public class TabMaker extends Application {
    public static List<String> strings = Arrays.asList(
            "A", "B", "C", "C#", "D", "E", "F#", "G", "G#",
            "F" // optional, only if you're accounting for enharmonic variants
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

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);

        Tab tab1 = new Tab("Guitar");
        tab1.setClosable(false);

        Tab tab2 = new Tab("Bass");
        tab2.setClosable(false);

        VBox guitarPanelContainer = new VBox();
        guitarPanelContainer.setSpacing(20);

        VBox bassPanelContainer = new VBox();
        bassPanelContainer.setSpacing(20);

        for (int i = 0; i < 6; i++) {
            guitarPanelContainer.getChildren().add(new TabPanel(strings.get(i)));
            if (i < 4) {
                bassPanelContainer.getChildren().add(new TabPanel(strings.get(i)));
            }
        }

        tab1.setContent(guitarPanelContainer);
        tab2.setContent(bassPanelContainer);

        tabPane.getTabs().addAll(tab1, tab2);

        root.getChildren().addAll(tabPane);

        Scene scene = new Scene(root);
        stage.setTitle("TabmakerFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}