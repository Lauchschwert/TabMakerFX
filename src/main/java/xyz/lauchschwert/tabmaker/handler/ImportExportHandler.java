package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanelAdapter;

import java.io.File;
import java.util.List;

public class ImportExportHandler {
    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(TabPanel.class, new TabPanelAdapter());

    private final Gson gson = gsonBuilder.create();

    public static File SAVE_DIRECTORY = new File(System.getenv("ProgramData") + "\\TabmakerFX\\Files\\Save");
    public static String VALID_IMPORTTYPE = ".json";

    private final String[] seperators = new String[]{";", ":"}; // Lines = ;, buttons = :

    private final TabPanelAdapter tba = new TabPanelAdapter();

    public ImportExportHandler() {
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

    public List<TabPanel> importTabPanels(File importFile) throws ImportException {
        return null;
    }
}
