package xyz.lauchschwert.tabmaker.core.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TmLogger {
    private static final Logger logger = Logger.getLogger(TmLogger.class.getName());
    private static final Path LOG_DIR = Paths.get(System.getProperty("user.home"), "TabMakerFx", "Files", "Logs");
    private static final String LOG_FILE = "tabmaker.log";

    static {
        initializeLogger();
    }

    private static void initializeLogger() {
        try {
            // Create the log directory
            Files.createDirectories(LOG_DIR);

            // Configure logger
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);

            addConsoleHandler();
            addFileHandler();
        } catch (IOException e) {
            System.err.println("FATAL: Failed to initialize logger: " + e.getMessage());
            System.err.println("Log directory: " + LOG_DIR);

            // Don't continue with broken logger
            throw new RuntimeException("Logger initialization failed", e);
        }
    }

    private static void addConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO); // Only INFO and above to console
        consoleHandler.setFormatter(new CustomFormatter());
        logger.addHandler(consoleHandler);
    }

    private static void addFileHandler() throws IOException {
        String logFilePath = LOG_DIR.resolve(LOG_FILE).toString();

        // File handler with rotation (5 files, 1MB each)
        FileHandler fileHandler = new FileHandler(logFilePath, 1024 * 1024, 5, true);
        fileHandler.setLevel(Level.ALL); // All levels to file
        fileHandler.setFormatter(new CustomFormatter());
        logger.addHandler(fileHandler);
    }

    // Public logging methods
    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.fine(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

    public static void error(String message) {
        logger.severe(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    // Application-specific logging methods
    public static void logUserAction(String action) {
        info("USER ACTION: " + action);
    }

    public static void logStartup() {
        info("=== TabMakerFX Started ===");
        info("Java Version: " + System.getProperty("java.version"));
        info("JavaFX Version: " + System.getProperty("javafx.version"));
        info("OS: " + System.getProperty("os.name"));
        info("User: " + System.getProperty("user.name"));
    }

    public static void logShutdown() {
        info("=== TabMakerFX Shutdown ===");
    }

    public static void logInitialization() {
        info("=== TabMakerFX initialized ===");
    }

    public static void logSystemExit(int i) {
        info("TabMakerFx closed with code: " + i);
        System.exit(i);
    }

}
