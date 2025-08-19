package xyz.lauchschwert.tabmaker.core.services.importexport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.TabPanel;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.adapters.InstrumentPanelAdapter;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.adapters.TabPanelAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TmImportExportService implements ImportExportService {
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

    public TmImportExportService() {
        final File SAVE_FOLDER = SAVE_PATH.toFile();
        if (!SAVE_FOLDER.exists() || !SAVE_FOLDER.isDirectory()) {
            boolean succeeded = SAVE_FOLDER.mkdirs();
            if (!succeeded) {
                TmLogger.error(SAVE_PATH + " could not be created.");
            }
        }
    }

    @Override
    public void handleInstrumentPanelExport(File destination, InstrumentPanel exportPanel) throws ExportException {
        if (exportPanel == null || destination == null) {
            throw new ExportException("Export failed because the destination path is null or the instrumentPanel is null");
        }

        String json = gson.toJson(
                exportPanel,
                InstrumentPanel.class
        );

        try {
            Files.writeString(destination.toPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            TmLogger.warn("Failed to write JSON to file: " + destination.getAbsolutePath() + "; " + e.getMessage());
            throw new ExportException("Export failed: " + e.getMessage());
        }
    }

    @Override
    public InstrumentPanel handleInstrumentPanelImport(File importFile) throws ImportException {
        try (FileReader fileReader = new FileReader(importFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            return gson.fromJson(bufferedReader, InstrumentPanel.class);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            throw new ImportException(e.getMessage());
        }
    }
}
