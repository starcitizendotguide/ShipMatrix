package de.sweetode.ShipMatrix.diff.types.compiled;

import com.google.gson.JsonObject;
import de.sweetode.ShipMatrix.diff.types.component.ComponentTypeFields;

import java.util.*;

public class CompiledData implements Cloneable {

    private final Map<ParentTypes, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields;

    public CompiledData(Map<ParentTypes, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields) {
        this.fields = Collections.unmodifiableMap(fields);
    }

    public Map<ParentTypes, Map<ComponentTypeFields, List<CompiledEntryContainer>>> getFields() {
        return this.fields;
    }

    public static CompiledData parse(JsonObject e) {

        Map<ParentTypes, Map<ComponentTypeFields, List<CompiledEntryContainer>>> fields = new LinkedHashMap<>();

        e.entrySet().forEach(parent -> {

            JsonObject element = parent.getValue().getAsJsonObject();
            Map<ComponentTypeFields, List<CompiledEntryContainer>> entries = new LinkedHashMap<>();
            element.entrySet().forEach(entry -> {

                List<CompiledEntryContainer> containers = new LinkedList<>();
                entry.getValue().getAsJsonArray().forEach(container -> containers.add(CompiledEntryContainer.parse(container.getAsJsonObject())));
                entries.put(ComponentTypeFields.byKey(entry.getKey()), containers);

            });
            fields.put(ParentTypes.byKey(parent.getKey()), entries);

        });

        return new CompiledData(fields);

    }

    @Override
    public CompiledData clone() {

        Map<ParentTypes, Map<ComponentTypeFields, List<CompiledEntryContainer>>> map = new HashMap<>();

        this.fields.forEach((key, value) -> {
            Map<ComponentTypeFields, List<CompiledEntryContainer>> _map = new HashMap<>();
            value.forEach((_key, _value) -> _map.put(_key, new ArrayList<>(_value)));
            map.put(key, _map);
        });

        return new CompiledData(map);

    }

}
