package xyz.lauchschwert.tabmaker.ui.panels.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.lang.reflect.Type;
import java.util.List;

public class InstrumentPanelAdapter implements JsonSerializer<InstrumentPanel>, JsonDeserializer<InstrumentPanel> {
    @Override
    public JsonElement serialize(InstrumentPanel src, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("tabPanels", context.serialize(
                src.getTabPanels(),
                new TypeToken<List<TabPanel>>(){}.getType())
        );

        return jsonObject;
    }

    @Override
    public InstrumentPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        List<TabPanel> tabPanels = context.deserialize(
                jsonObject.get("tabPanels"),
                new TypeToken<List<TabPanel>>() {}.getType()
        );

        InstrumentPanel panel = new InstrumentPanel(tabPanels);

        return null;
    }
}
