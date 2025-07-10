package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.InstrumentPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.TabPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportExportHandler {
    private static final String INITIAL_NAME = "save";
    private static final String INITIAL_EXTENSION = ".json";
    public static final String INITIAL_FILE_NAME = INITIAL_NAME + INITIAL_EXTENSION;

    public static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "TabMakerFX", "Files", "Saves");

    public static String VALID_IMPORTTYPE = "*.json";

    private final TabMaker tabMaker;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter())
            .registerTypeAdapter(InstrumentPanel.class, new InstrumentPanelAdapter());
    private final Gson gson = gsonBuilder.create();

    public ImportExportHandler(TabMaker tabMaker) {
        this.tabMaker = tabMaker;

        final File SAVE_FOLDER = SAVE_PATH.toFile();

        if (!SAVE_FOLDER.exists() || !SAVE_FOLDER.isDirectory()) {
            boolean succeeded = SAVE_FOLDER.mkdirs();
            if (!succeeded) {
                TmLogger.error(SAVE_PATH + " could not be created.");
            }
        }
    }

    public void handleExport() {
        // get current tab from TabMaker
        TmTab selectedTab = (TmTab) tabMaker.getSelectedTab();
        InstrumentPanel targetPanel = selectedTab.getInstrumentPanel();

        String json = gson.toJson(
                targetPanel,
                InstrumentPanel.class
        );

        int count = 0;
        File file;

        while (true) {
            String filename = count == 0 ? INITIAL_FILE_NAME : INITIAL_NAME + count + INITIAL_EXTENSION;
            file = new File(SAVE_PATH.toFile(), filename);

            if (!file.exists()) {
                break; // Found available filename
            }
            count++;
        }
        saveFileViaFileChooser(json, new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE));
    }

    public void handleImport() throws ImportException {
        File importFile = TabMaker.GetFileViaFileChooser(
                new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE) // later on text files etc....
        );
        if (importFile == null || importFile.isDirectory() || !importFile.canRead()) {
            throw new ImportException("Couldn't import file since it doesn't exist, cannot be read or is a directory!");
        }

        try (FileReader fileReader = new FileReader(importFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            InstrumentPanel instrumentPanel = gson.fromJson(bufferedReader, InstrumentPanel.class);

            TmTab tab = (TmTab) tabMaker.createNewTab("instrumentPanel");
            tab.setInstrumentPanel(instrumentPanel);
        } catch (IOException e) {
            throw new ImportException(e.getMessage());
        }
    }

    public void saveFileViaFileChooser(String content, FileChooser.ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");

        // Add extension filters
        fc.getExtensionFilters().addAll(filters);

        // Set initial directory
        // Replace with save directory from config loader :)
        fc.setInitialDirectory(Paths.get(System.getProperty("user.home"), "TabMakerFX", "Files", "Saves").toFile());
        fc.setInitialFileName(ImportExportHandler.INITIAL_FILE_NAME);

        File file = fc.showSaveDialog(null);

        if (file != null) {
            try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write(content);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setContentText("Failed to save file: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
