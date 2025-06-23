package xyz.lauchschwert.tabmaker.export;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONExporter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void SaveToJSON(String string, List<String> stringTabs) {
        final Map<String, List<String>> stringListMap = new HashMap<String, List<String>>();
        stringListMap.put(string, stringTabs);

        String json = gson.toJson(stringListMap);
    }
}
