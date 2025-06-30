package xyz.lauchschwert.tabmaker.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.TabPanel;

import java.io.*;

import java.util.List;

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
            throw new ImportException("File does not exist is a directory or cannot be read.");
        }
        // filetype check
        if (!importFile.getName().endsWith(VALID_IMPORTTYPE)) {
            throw new ImportException("File is not a json file.");
        }

        StringBuilder jsonString = new StringBuilder();
        try {
            FileReader fr = new FileReader(importFile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            throw new ImportException("Error at import: " + e.getMessage());
        }

        return gson.fromJson(jsonString.toString(), new TypeToken<List<TabPanel>>(){}.getType());
    }

    public static void ExportTabPanels(List<TabPanel> tabPanels) {
        String json = gson.toJson(tabPanels, new TypeToken<List<TabPanel>>(){}.getType());
        System.out.println("json: " + json);
    }
}
