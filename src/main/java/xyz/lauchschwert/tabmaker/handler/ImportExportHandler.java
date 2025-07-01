package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Tab;
import xyz.lauchschwert.tabmaker.TabMaker;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanelAdapter;

import java.io.File;
import java.util.List;

public class ImportExportHandler {
    public static File SAVE_DIRECTORY = new File(System.getenv("ProgramData") + "\\TabmakerFX\\Files\\Save");
    public static String VALID_IMPORTTYPE = ".json";

    private final TabMaker tabMaker;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter());
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

    public void exportTabPanels(List<TabPanel> tabPanels) {
        String json = gson.toJson(tabPanels, new TypeToken<List<TabPanel>>() {
        }.getType());
        System.out.println("json: " + json);
    }

    public void handleImport(File importFile) throws ImportException {
        if (!importFile.exists() || !importFile.canRead() || importFile.isDirectory()) {
            throw new ImportException("Couldn't import file since it doesn't exist, cannot be read or is a directory!");
        }

        System.out.println(importFile.getAbsolutePath());
    }
}
