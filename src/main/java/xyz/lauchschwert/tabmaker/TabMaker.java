package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.handler.ConfigService;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    private UserInterface userInterface;
    private ConfigService configService;

    @Override
    public void init() {
        // init code here
        this.configService = ConfigService.getInstance();
        configService.initConfigFiles();
        configService.loadProperties();

        userInterface = new UserInterface();

        TmLogger.logInitialization();
    }

    @Override
    public void start(Stage stage) {
        setScene(stage);
        TmLogger.logStartup();
    }

    @Override
    public void stop() {
        TmLogger.logShutdown();
    }

    private void setScene(Stage stage) {
        Scene scene = userInterface.createScene(stage);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        stage.setTitle("TabMakerFx");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);

        final String widthProp = configService.find("ui.window.width");
        final String heightProp = configService.find("ui.window.height");

        try {
            double width = Double.parseDouble(widthProp);
            double height = Double.parseDouble(heightProp);
            stage.setWidth(width);
            stage.setHeight(height);
        } catch (NumberFormatException e) {
            TmLogger.warn("Error setting height and width property. Falling back to default values");
            stage.setWidth(DEFAULT_WIDTH);
            stage.setHeight(DEFAULT_HEIGHT);
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void run() {
        launch();
    }
}