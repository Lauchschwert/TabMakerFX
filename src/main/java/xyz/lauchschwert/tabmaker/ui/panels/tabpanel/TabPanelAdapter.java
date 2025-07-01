package xyz.lauchschwert.tabmaker.ui.panels.tabpanel;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TabPanelAdapter implements JsonSerializer<TabPanel>, JsonDeserializer<TabPanel> {
    @Override
    public JsonElement serialize(TabPanel src, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("string", src.getStringName());
        jsonObject.add("notes", jsonSerializationContext.serialize(src.getNotes(), String[].class));

        return jsonObject;
    }

    @Override
    public TabPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        System.out.println("jsonElement: " + jsonElement);

        return null;
    }
}
