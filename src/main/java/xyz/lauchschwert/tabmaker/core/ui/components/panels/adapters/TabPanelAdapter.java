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
        jsonObject.add("features", context.serialize(src.getFeatures(), String[].class));

        TmLogger.info("Serialized TabPanel: " + jsonObject);

        return jsonObject;
    }

    @Override
    public TabPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject object = jsonElement.getAsJsonObject();

        final JsonArray notesObject = object.getAsJsonArray("notes");

        final JsonArray featuresObject = object.getAsJsonArray("features");

        String[] notes = context.deserialize(notesObject, String[].class);
        if (notes == null) {
            TmLogger.warn("Failed to deserialize TabPanel, because notes are null.");
            return null;
        }

        String[] features = context.deserialize(featuresObject, String[].class);
        if (features == null) {
            TmLogger.warn("Failed to deserialize TabPanel, because features are null.");
            return null;
        }

        String string = object.get("string").getAsString();
        if (string == null) {
            TmLogger.warn("Failed to deserialize TabPanel, because string is null.");
            return null;
        }

        return new TabPanel(string, notes, features);
    }
}
