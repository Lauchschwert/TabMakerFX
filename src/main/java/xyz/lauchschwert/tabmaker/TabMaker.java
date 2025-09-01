package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.config.TmConfigService;
import xyz.lauchschwert.tabmaker.core.services.file.TmFileService;
import xyz.lauchschwert.tabmaker.core.ui.UserInterface;

import java.util.Objects;

public class TabMaker extends Application {
    private static final double DEFAULT_WIDTH = 1000;
    private static final double DEFAULT_HEIGHT = 600;
    private static final String APP_NAME = "TabMakerFx";

    private UserInterface userInterface;
    private TmConfigService configService;
    private TmFileService fileService;

    @Override
    public void init() {
        TmLogger.logInitialization();

        // init code here
        this.fileService = new TmFileService();
        TmLogger.info("File Service initialized.");

        this.configService = new TmConfigService(fileService);
        TmLogger.info("Config Service initialized.");

        userInterface = new UserInterface();
        TmLogger.info("User Interface initialized.");

        TmLogger.info("=== TabMakerFx initialized ===");
    }

    @Override
    public void start(Stage stage) {
        TmLogger.logStartup();

        setScene(stage);
        TmLogger.info("=== TabMakerFx started ===");
    }

    @Override
    public void stop() {
        TmLogger.logShutdown();

        TmLogger.info("=== TabMakerFx stopped ===");
    }

    private void setScene(Stage stage) {
        Scene scene = userInterface.createScene(stage);
        TmLogger.info("Scene created.");

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/TabMaker.css")).toExternalForm());
        TmLogger.info("Stylesheet added to scene.");

        stage.setTitle(APP_NAME);
        TmLogger.info("Stage title set to " + APP_NAME);

        stage.setScene(scene);
        TmLogger.info("Scene set.");

        stage.setMinWidth(600);
        stage.setMinHeight(400);
        TmLogger.info("Stage minimum size set.");

        initWidthAndHeight(stage);
        TmLogger.info("Stage width and height applied.");

        stage.show();
        TmLogger.info("Showing stage.");
    }

    private void initWidthAndHeight(Stage stage) {
        stage.setWidth(DEFAULT_WIDTH);
        stage.setHeight(DEFAULT_HEIGHT);
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public static void main(String[] args) {
        launch(args);
    }
}