package xyz.lauchschwert.tabmaker.handler;

import javafx.scene.control.Alert;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigHandler {
    private static ConfigHandler instance;

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), "Documents", "TabMakerFx", "Files", "Config");
    private static final Path TEMPLATE_PATH = Paths.get(String.valueOf(ConfigHandler.class.getResourceAsStream("/templates/configs/")));

    private File[] propFiles; // TODO: Change to Set<>
    private List<Properties> propList;

    public ConfigHandler() {
        init(); // Creates default files/dirs
    }

    private void init() {
        File configDir = (CONFIG_PATH.toFile());
        if (!configDir.exists() || !configDir.isDirectory()) {
            boolean success = configDir.mkdirs();
            TmLogger.info("Config Directory created.");
            if (!success) {
                TmLogger.error("Failed to create config directory: " + CONFIG_PATH);

                UserInterface.ShowAlert(
                        Alert.AlertType.ERROR,
                        "Startup Error",
                        "Cannot Create Configuration Directory",
                        "The application cannot create the required configuration directory at:\n" + CONFIG_PATH + "\n\nPlease check permissions and try again."
                );

                TmLogger.logSystemExit(1);
            }
        }
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
        Path defaultPropertiesPath = CONFIG_PATH.resolve("default.properties");

        File[] files = CONFIG_PATH.toFile().listFiles();
        if (files == null) {
            TmLogger.error("Could not find any config files in directory: " + CONFIG_PATH);
            TmLogger.logSystemExit(1);
            return;
        }

        propFiles = new File[files.length];
        propList = new ArrayList<>();

        int counter = 0;

        for (File file : files) {
            if (file.getName().endsWith(".properties")) {
                if (counter >= propFiles.length) {
                    TmLogger.info("Found " + counter + " property files.");
                    break;
                }
                propFiles[counter] = file;
                counter++;
                TmLogger.info("Found property file: " + file.getAbsolutePath());
            }
        }


        try (InputStream templateStream = getClass().getResourceAsStream("/templates/configs/default.properties")) {
            if (templateStream == null) {
                TmLogger.error("Default.properties file does not exist.");
                TmLogger.error("This indicates a build/packaging problem.");
                TmLogger.logSystemExit(1);
                return;
            }

            if (!Files.exists(defaultPropertiesPath)) {
                Files.createDirectories(defaultPropertiesPath.getParent());
                Files.copy(templateStream, defaultPropertiesPath, StandardCopyOption.REPLACE_EXISTING);

                TmLogger.info("Created default config file from template.");
            }
        } catch (IOException e) {
            TmLogger.error("Failed to load or create default properties file.");
            TmLogger.logSystemExit(1);
        }
    }

    public void loadProperties() {
        if (propFiles == null) {
            TmLogger.error("No properties found to load");
            TmLogger.logSystemExit(1);
        }

        for (File file : this.propFiles) {
            if (file.getName().endsWith(".properties")) {
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream(file)) {
                    prop.load(fis);
                    if (!propList.contains(prop)) {
                        propList.add(prop);
                        TmLogger.info("Loaded properties file: " + file.getAbsolutePath());
                    } else {
                        TmLogger.info("Skipped duplicate properties file: " + file.getAbsolutePath());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String find(String key) {
        if (propList == null || propList.isEmpty()) {
            return null;
        }

        for (Properties prop : propList) {
            if (prop.containsKey(key)) {
                TmLogger.info("Found existing property in prop file");
                return prop.getProperty(key);
            }
        }

        return null;
    }
}
