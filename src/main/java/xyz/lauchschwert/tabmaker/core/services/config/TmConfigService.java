package xyz.lauchschwert.tabmaker.core.services.config;

import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.services.file.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TmConfigService implements ConfigService {

    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.dir"), "TabMakerFx", "Files", "Config");
    private static final Path TEMPLATE_PATH = Paths.get(String.valueOf(TmConfigService.class.getResourceAsStream("/templates/configs/")));

    private final FileService fileService;

    public TmConfigService(FileService fileService) {
        this.fileService = fileService;
        init(); // Creates default files/dirs
    }

    private void init() {
        if (fileService.getExistingFile(CONFIG_PATH) != null) {
            return;
        }

        final File configPathFile = CONFIG_PATH.toFile();
        try {
            if (!configPathFile.mkdirs() && !configPathFile.exists()) {
                throw new IOException("Failed to create directory: " + CONFIG_PATH);
            }
            TmLogger.info("Config directory created successfully: " + CONFIG_PATH);

        } catch (SecurityException | IOException e) {
            TmLogger.error("Cannot create config directory: " + e.getMessage());
            TmLogger.logSystemExit(1);
            return;
        }

        if (!fileService.isReadable(configPathFile)) {
            TmLogger.error("ConfigService could not be initialized because the configuration directory cannot be read!");
            TmLogger.logSystemExit(1);
        } else if (!configPathFile.isDirectory()) {
            TmLogger.error("ConfigService could not be initialized because the configuration directory is not a directory!");
            TmLogger.logSystemExit(1);
        }
    }

    @Override
    public Properties loadProperties(File file) {
        if (file == null) {
            TmLogger.info("Could not load properties file, because the file is null.");
            return null;
        }

        if (file.getName().endsWith(".properties")) {
            if (!fileService.isReadable(file)) {
                TmLogger.warn("Could not load properties file, because the file is not readable!");
                return null;
            }

            try (FileInputStream fis = new FileInputStream(file)) {
                final Properties properties = new Properties();
                properties.load(fis);
                if (properties.isEmpty()) {
                    TmLogger.info("Properties file is empty.");
                    return null;
                }
                return properties;
            } catch (Exception e) {
                TmLogger.warn("Failed to load properties file: " + file.getAbsolutePath() + "; " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
