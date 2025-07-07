package xyz.lauchschwert.tabmaker.handler;

import javafx.scene.control.Alert;
import xyz.lauchschwert.tabmaker.logging.TmLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class ConfigHandler {
    private static ConfigHandler instance;

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), "Documents", "TabMakerFx", "Files", "Config");

    public ConfigHandler() {
        init(); // Creates default files/dirs
    }

    private void init() {
        File configDir = (CONFIG_PATH.toFile());
        if (!configDir.exists() || !configDir.isDirectory()) {
            boolean success = configDir.mkdirs();
            if (!success) {
                TmLogger.error("Failed to create config directory: " + CONFIG_PATH);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Startup Error");
                alert.setHeaderText("Cannot Create Configuration Directory");
                alert.setContentText("The application cannot create the required configuration directory at:\n" + CONFIG_PATH + "\n\nPlease check permissions and try again.");
                alert.showAndWait();

                TmLogger.logSystemExit(1);
            }
        }
        TmLogger.info("Config Directory created.");
    }

    public static ConfigHandler getInstance() {
        // Creates a new instance if no instance was created before
        if (instance == null) {
            instance = new ConfigHandler();
            TmLogger.info("ConfigHandler instance created.");
        }
        return instance;
    }

    public void initConfigFiles() {
        // Here the application checks if config files are non-existent and create them if so from a default template
        Path targetFile = CONFIG_PATH.resolve("default.properties");
        if (Files.exists(targetFile)) {
            TmLogger.info("Existing config file found.");
            return;
        }
        try (InputStream templateStream = getClass().getResourceAsStream("/templates/configs/default.properties")) {
            if (templateStream == null) {
                TmLogger.error("Default.properties file does not exist.");
                TmLogger.error("This indicates a build/packaging problem.");
                TmLogger.logSystemExit(1);
                return;
            }
            Files.createDirectories(targetFile.getParent());

            Files.copy(templateStream, targetFile, StandardCopyOption.REPLACE_EXISTING);

            TmLogger.info("Created default config file from template.");
        } catch (IOException e) {
            TmLogger.error("Failed to load or create default properties file.");
            TmLogger.logSystemExit(1);
        }
    }

    public Properties loadProperties() {
        try (InputStream input = Files.newInputStream(CONFIG_PATH.resolve("default.properties"))) {
            Properties props = new Properties();
            props.load(input);
            TmLogger.info("Loaded default properties.");
            return props;
        } catch (IOException e) {
            TmLogger.error("Failed to load default properties.");
            TmLogger.logSystemExit(1);
            return null;
        }
    }
}
