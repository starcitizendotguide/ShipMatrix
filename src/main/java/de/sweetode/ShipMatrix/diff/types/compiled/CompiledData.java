package de.sweetode.ShipMatrix.diff.types.compiled;

import com.google.gson.JsonObject;
import de.sweetode.ShipMatrix.diff.types.component.ComponentTypeFields;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CompiledData {

    private final Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields;

    public CompiledData(Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields) {
        this.fields = fields;
    }

    public Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> getFields() {
        return fields;
    }

    public static CompiledData parse(JsonObject e) {

        Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields = new LinkedHashMap<>();

        e.entrySet().forEach(parent -> {

            JsonObject element = parent.getValue().getAsJsonObject();
            Map<ComponentTypeFields, List<CompiledEntryContainer>> entries = new LinkedHashMap<>();
            element.entrySet().forEach(entry -> {

                List<CompiledEntryContainer> containers = new LinkedList<>();
                entry.getValue().getAsJsonArray().forEach(container -> containers.add(CompiledEntryContainer.parse(container.getAsJsonObject())));
                entries.put(ComponentTypeFields.byKey(entry.getKey()), containers);

            });
            fields.put(parent.getKey(), entries);

        });

        return new CompiledData(fields);

    }

}
