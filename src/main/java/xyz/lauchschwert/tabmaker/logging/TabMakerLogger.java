package xyz.lauchschwert.tabmaker.logging;

import java.io.File;
import java.util.logging.Logger;

public class TabMakerLogger {
    private static final Logger logger = Logger.getLogger(TabMakerLogger.class.getName());
    private static final File LOGGING_DIR = new File(System.getenv("user.home"), "tabmaker" + File.separator + "logs");
}
