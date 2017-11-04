package de.sweetode.ShipMatrix.diff.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public interface DataField extends JsonSerializer<DataField> {

    String getName();

    String getKey();

    default String getUnit() {
        return "<>";
    }

    @Override
    default JsonElement serialize(DataField field, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject value = new JsonObject();

        value.addProperty("name", field.getName());
        value.addProperty("key", field.getKey());
        value.addProperty("unit", field.getUnit());

        return value;

    }

}
