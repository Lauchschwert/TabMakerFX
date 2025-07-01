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

import java.io.File;

public class ImportExportHandler {
    public static File SAVE_DIRECTORY = new File(System.getenv("ProgramData") + "\\TabmakerFX\\Files\\Save\\");
    public static String VALID_IMPORTTYPE = "*.json";

    private final TabMaker tabMaker;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter())
            .registerTypeAdapter(InstrumentPanel.class, new InstrumentPanelAdapter());
    private final Gson gson = gsonBuilder.create();

    public ImportExportHandler(TabMaker tabMaker) {
        this.tabMaker = tabMaker;
        if (!SAVE_DIRECTORY.exists()) {
            boolean succeeded = SAVE_DIRECTORY.mkdirs();
            if (!succeeded) {
                throw new ImportException("Unable to create save directory.");
            }
        }
    }

    public void handleExport(InstrumentPanel targetPanel) {
        int count = 0;
        File file;

        while (true) {
            String filename = count == 0 ? "save.json" : "save" + count + ".json";
            file = new File(SAVE_DIRECTORY, filename);

            if (!file.exists()) {
                break; // Found available filename
            }
            count++;
        }

        String json = gson.toJson(
                targetPanel,
                InstrumentPanel.class
        );

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
