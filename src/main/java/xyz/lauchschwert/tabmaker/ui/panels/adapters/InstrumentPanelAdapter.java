package xyz.lauchschwert.tabmaker.ui.panels.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import xyz.lauchschwert.tabmaker.enums.InstrumentType;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.ui.panels.tabpanel.TabPanel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class InstrumentPanelAdapter implements JsonSerializer<InstrumentPanel>, JsonDeserializer<InstrumentPanel> {
    @Override
    public JsonElement serialize(InstrumentPanel src, Type type, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }

        JsonObject jsonObject = new JsonObject();

        // Null-safe serialization
        InstrumentType instrumentType = src.getInstrumentType();
        if (instrumentType != null) {
            jsonObject.add("instrumentType", context.serialize(instrumentType));
        }

        List<TabPanel> tabPanels = src.getTabPanels();
        if (tabPanels != null) {
            jsonObject.add("tabPanels", context.serialize(tabPanels));
        }

        return jsonObject;
    }

    @Override
    public InstrumentPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (jsonElement == null || !jsonElement.isJsonObject()) {
            throw new JsonParseException("Expected JsonObject but got: " + jsonElement);
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Safe deserialization with null checks
        InstrumentType instrumentType = null;
        if (jsonObject.has("instrumentType") && !jsonObject.get("instrumentType").isJsonNull()) {
            instrumentType = context.deserialize(jsonObject.get("instrumentType"), InstrumentType.class);
        }

        List<TabPanel> tabPanels = null;
        if (jsonObject.has("tabPanels") && !jsonObject.get("tabPanels").isJsonNull()) {
            tabPanels = context.deserialize(
                    jsonObject.get("tabPanels"),
                    new TypeToken<List<TabPanel>>() {}.getType()
            );
        }

        // Make sure this constructor exists!
        return new InstrumentPanel(Objects.requireNonNull(instrumentType), tabPanels);
    }
}
