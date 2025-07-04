package xyz.lauchschwert.tabmaker.handler;

import xyz.lauchschwert.tabmaker.logging.TmLogger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigHandler {
    private static ConfigHandler instance;

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.home"), "TabMakerFx", "Files", "Config");

    public ConfigHandler() {
        init(); // Creates default files/dirs
    }

    private void init() {
        File configDir = (CONFIG_PATH.toFile());
        if (!configDir.exists() || !configDir.isDirectory()) {
            boolean success = configDir.mkdirs();
            if (!success) {
                TmLogger.error(CONFIG_PATH + " could not be created.");
            }
        }
    }

    public static ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public void initConfigFiles() {
        
    }
}
