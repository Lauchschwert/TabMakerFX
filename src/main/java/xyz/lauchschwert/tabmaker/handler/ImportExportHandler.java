package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.TabPanel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExportHandler {
    public static File SAVE_DIRECTORY = new File(System.getenv("ProgramData") + "\\TabmakerFX\\Files\\Save");
    public static String VALID_IMPORTTYPE = ".json";

    private String[] seperators = new String[] {";" , ":"}; // Lines = ;, buttons = :

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ImportExportHandler(){
        if (!SAVE_DIRECTORY.exists()) {
            boolean succeeded = SAVE_DIRECTORY.mkdirs();
            if (!succeeded) {
                throw new ImportException("Unable to create save directory.");
            }
        }
    }

    public static List<TabPanel> ImportTabPanels(File importFile) throws ImportException {
        // basic check for any invalid files
        if (!importFile.exists() || importFile.isDirectory() || !importFile.canRead()) {
            throw new ImportException("File does not exist is not a directory or cannot be read.");
        }
        // filetype check
        if (!importFile.getName().endsWith(VALID_IMPORTTYPE)) {
            throw new ImportException("File is not a json file.");
        }



        return null;
    }
}
