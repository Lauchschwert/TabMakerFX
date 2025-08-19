package xyz.lauchschwert.tabmaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.TmConfigService;
import xyz.lauchschwert.tabmaker.core.services.TmFileService;
import xyz.lauchschwert.tabmaker.core.ui.UserInterface;

import java.util.Objects;

public class TabMaker extends Application {
    private static final double DEFAULT_WIDTH = 1000;
    private static final double DEFAULT_HEIGHT = 600;

    private UserInterface userInterface;
    private TmConfigService configService;
    private TmFileService fileService;

    @Override
    public void init() {
        // init code here
        this.fileService = new TmFileService();
        this.configService = new TmConfigService(fileService);

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

        initWidthAndHeight(stage);

        stage.show();
    }

    private void initWidthAndHeight(Stage stage) {
        stage.setWidth(DEFAULT_WIDTH);
        stage.setHeight(DEFAULT_HEIGHT);

    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public static void main(String[] args) {
        launch();
    }

    public void run() {
        launch();
    }
}