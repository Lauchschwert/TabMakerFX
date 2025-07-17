package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.dialog.DialogService;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.InstrumentPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.TabPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportExportService {
    private static final String INITIAL_NAME = "save";
    private static final String INITIAL_EXTENSION = ".json";
    public static final String INITIAL_FILE_NAME = INITIAL_NAME + INITIAL_EXTENSION;

    public static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "TabMakerFx", "Files", "Saves");

    public static String VALID_IMPORTTYPE = "*.json";

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(InstrumentPanel.class, new InstrumentPanelAdapter())
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter());

    private final Gson gson = gsonBuilder.create();

    public ImportExportService() {
        final File SAVE_FOLDER = SAVE_PATH.toFile();
        if (!SAVE_FOLDER.exists() || !SAVE_FOLDER.isDirectory()) {
            boolean succeeded = SAVE_FOLDER.mkdirs();
            if (!succeeded) {
                TmLogger.error(SAVE_PATH + " could not be created.");
            }
        }
    }

    public void handleExport(File destination, InstrumentPanel exportPanel) {
        if (exportPanel == null) {
            DialogService.ShowAlert(Alert.AlertType.ERROR, "Failed", "Import of panel failed", "The panel could not be parsed!");
            return;
        }

        String json = gson.toJson(
                exportPanel,
                InstrumentPanel.class
        );

        try {
            Files.writeString(destination.toPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            TmLogger.warn("Failed to write JSON to file: " + destination.getAbsolutePath() + "; " + e.getMessage());
        }
    }

    public InstrumentPanel handleImport(File file) throws ImportException {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            return gson.fromJson(bufferedReader, InstrumentPanel.class);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            throw new ImportException(e.getMessage());
        }
    }
}
