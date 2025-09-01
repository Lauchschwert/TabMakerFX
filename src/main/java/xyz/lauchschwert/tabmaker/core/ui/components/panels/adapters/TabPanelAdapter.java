package xyz.lauchschwert.tabmaker.core.ui.components.panels.adapters;

import com.google.gson.*;
import xyz.lauchschwert.tabmaker.core.logging.TmLogger;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.TabPanel;

import java.lang.reflect.Type;

public class TabPanelAdapter implements JsonSerializer<TabPanel>, JsonDeserializer<TabPanel> {
    @Override
    public JsonElement serialize(TabPanel src, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("string", src.getStringName());
        jsonObject.add("notes", context.serialize(src.getNotes(), String[].class));

        TmLogger.info("Serialized TabPanel: " + jsonObject);

        return jsonObject;
    }

    @Override
    public TabPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject object = jsonElement.getAsJsonObject();

        JsonArray notesObject = object.getAsJsonArray("notes");

        String[] notes = context.deserialize(notesObject, String[].class);
        if (notes == null) {
            TmLogger.warn("Failed to deserialize TabPanel, because notes are null.");
            return null;
        }

        String string = object.get("string").getAsString();
        if (string == null) {
            TmLogger.warn("Failed to deserialize TabPanel, because string is null.");
            return null;
        }

        return new TabPanel(string, notes);
    }
}
