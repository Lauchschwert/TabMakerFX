package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.InstrumentPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.adapters.TabPanelAdapter;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.io.File;

public class ImportExportHandler {
    public static File SAVE_DIRECTORY = new File(System.getenv("ProgramData") + "\\TabmakerFX\\Files\\Save");
    public static String VALID_IMPORTTYPE = ".json";

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
        String json = gson.toJson(
                targetPanel,
                InstrumentPanel.class
        );

        System.out.println("json: " + json);
    }

    public void handleImport(File importFile) throws ImportException {
        if (!importFile.exists() || !importFile.canRead() || importFile.isDirectory()) {
            throw new ImportException("Couldn't import file since it doesn't exist, cannot be read or is a directory!");
        }

        System.out.println(importFile.getAbsolutePath());
    }
}
