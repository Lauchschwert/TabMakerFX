package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.handler.ConfigHandler;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;

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

    private Properties applicationProps;

    private UserInterface userInterface;

    @Override
    public void init() {
        TmLogger.logInitialization();
        // init code here
        ConfigHandler.getInstance().initConfigFiles(); // getInstance() also initializes the ConfigHandler
        applicationProps = ConfigHandler.getInstance().loadProperties();

        userInterface = new UserInterface(this);

        TmLogger.logStartup();
    }

    @Override
    public void start(Stage stage) {
        setScene(stage);
    }

    @Override
    public void stop() {
        TmLogger.logShutdown();
    }

    private void setScene(Stage stage) {
        Scene scene = userInterface.createScene();
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

    public static void main(String[] args) {
        launch();
    }

    public void run() {
        launch();
    }

    public UserInterface getUI() {
        return userInterface;
    }

    public Tab createNewTab(String tabName) {
        return userInterface.createNewTab(tabName);
    }
}