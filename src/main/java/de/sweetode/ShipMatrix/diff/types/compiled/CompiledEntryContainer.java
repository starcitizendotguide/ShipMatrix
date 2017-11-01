package de.sweetode.ShipMatrix.diff.types.compiled;

import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class CompiledEntryContainer {

    private final Map<CompiledFields, String> values;

    public CompiledEntryContainer(Map<CompiledFields, String> values) {
        this.values = values;
    }

    public Map<CompiledFields, String> getValues() {
        return this.values;
    }

    public static CompiledEntryContainer parse(JsonObject entry) {

        Map<CompiledFields, String> values = new LinkedHashMap<>();

        for (CompiledFields field : CompiledFields.values()) {
            if(entry.has(field.getKey())) {
                values.put(field, entry.get(field.getKey()).getAsString());
            }
        }

        return new CompiledEntryContainer(values);

    }

}
