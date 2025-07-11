package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.logging.TmLogger;
import xyz.lauchschwert.tabmaker.ui.UserInterface;
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

    public static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "TabMakerFx", "Files", "Saves");

    public static String VALID_IMPORTTYPE = "*.json";

    private final UserInterface userInterface;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter())
            .registerTypeAdapter(InstrumentPanel.class, new InstrumentPanelAdapter());
    private final Gson gson = gsonBuilder.create();

    public ImportExportHandler(UserInterface userInterface) {
        this.userInterface = userInterface;

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
        TmTab selectedTab = (TmTab) userInterface.getSelectedTab();
        InstrumentPanel targetPanel = selectedTab.getInstrumentPanel();

        String json = gson.toJson(
                targetPanel,
                InstrumentPanel.class
        );

        saveFileViaFileChooser(json, new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE));
    }

    public InstrumentPanel handleImport() throws ImportException {
        File importFile = userInterface.getFileViaFileChooser(
                new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE) // later on text files etc....
        );
        if (importFile == null || importFile.isDirectory() || !importFile.canRead()) {
            throw new ImportException("Couldn't import file since it doesn't exist, cannot be read or is a directory!");
        }

        try (FileReader fileReader = new FileReader(importFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            return gson.fromJson(bufferedReader, InstrumentPanel.class);
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
        fc.setInitialDirectory(SAVE_PATH.toFile());
        fc.setInitialFileName(ImportExportHandler.INITIAL_FILE_NAME);

        final File file = fc.showSaveDialog(null);

        if (file != null) {
            try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            } catch (IOException e) {
                UserInterface.ShowAlert(
                        Alert.AlertType.ERROR,
                        "Save Error",
                        "An error has occurred",
                        "Failed to save file: " + e.getMessage()
                );
            }
        }
    }
}
