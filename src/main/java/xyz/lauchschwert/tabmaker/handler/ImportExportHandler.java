package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.InstrumentPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.TabPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;
import xyz.lauchschwert.tabmaker.ui.tabs.TmTab;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportExportHandler {
    private static final String INITIAL_NAME = "save";
    private static final String INITIAL_EXTENSION = ".json";
    public static final String INITIAL_FILE_NAME = INITIAL_NAME + INITIAL_EXTENSION;

    private static final String saveDirString = System.getProperty("user.home") + "\\TabmakerFX\\Files\\Saves\\";
    public static final Path SAVE_DIRECTORY = Paths.get(saveDirString);

    public static String VALID_IMPORTTYPE = "*.json";

    private final TabMaker tabMaker;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter())
            .registerTypeAdapter(InstrumentPanel.class, new InstrumentPanelAdapter());
    private final Gson gson = gsonBuilder.create();

    public ImportExportHandler(TabMaker tabMaker) {
        this.tabMaker = tabMaker;

        final File SAVE_FOLDER = SAVE_DIRECTORY.toFile();

        if (!SAVE_FOLDER.exists()) {
            boolean succeeded = SAVE_FOLDER.mkdirs();
            if (!succeeded) {
                throw new ImportException("Unable to create save directory.");
            }
        }
    }

    public void handleExport() {
        // get current tab from TabMaker
        TmTab selectedTab = tabMaker.getSelectedTab();
        InstrumentPanel targetPanel = selectedTab.getInstrumentPanel();

        String json = gson.toJson(
                targetPanel,
                InstrumentPanel.class
        );

        int count = 0;
        File file;

        while (true) {
            String filename = count == 0 ? INITIAL_FILE_NAME : INITIAL_NAME + count + INITIAL_EXTENSION;
            file = new File(SAVE_DIRECTORY.toFile(), filename);

            if (!file.exists()) {
                break; // Found available filename
            }
            count++;
        }
        TabMaker.SaveFileViaFileChooser(json, new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE));
    }

    public void handleImport() throws ImportException {
        File importFile = TabMaker.GetFileViaFileChooser(
                new FileChooser.ExtensionFilter("JSON Files", VALID_IMPORTTYPE)
        );
        if (importFile == null) {
            throw new ImportException("Couldn't import file since it doesn't exist, cannot be read or is a directory!");
        }

        // Optional: Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import Successful");
        alert.setContentText("File imported successfully!");
        alert.showAndWait();

        System.out.println(importFile.getAbsolutePath());
    }
}
