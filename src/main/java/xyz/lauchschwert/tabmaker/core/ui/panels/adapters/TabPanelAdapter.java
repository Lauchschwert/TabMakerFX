package xyz.lauchschwert.tabmaker.core.ui.panels.adapters;

import com.google.gson.*;
import xyz.lauchschwert.tabmaker.core.ui.panels.TabPanel;

import java.lang.reflect.Type;

public class TabPanelAdapter implements JsonSerializer<TabPanel>, JsonDeserializer<TabPanel> {
    @Override
    public JsonElement serialize(TabPanel src, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("string", src.getStringName());
        jsonObject.add("notes", context.serialize(src.getNotes(), String[].class));

        return jsonObject;
    }

    @Override
    public TabPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject object = jsonElement.getAsJsonObject();

        JsonArray notesObject = object.getAsJsonArray("notes");
        String[] notes = context.deserialize(notesObject, String[].class);

        String string = object.get("string").getAsString();

        return new TabPanel(string, notes);
    }
}
