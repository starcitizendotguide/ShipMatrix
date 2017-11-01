package de.sweetode.ShipMatrix.diff;

import com.google.gson.JsonObject;
import de.sweetode.ShipMatrix.diff.types.DataField;
import de.sweetode.ShipMatrix.diff.types.ManufacturerFields;

import java.util.LinkedHashMap;
import java.util.Map;

public class Manufacturer {

    private final Map<DataField, String> values;

    public Manufacturer(Map<DataField, String> values) {
        this.values = values;
    }

    public Map<DataField, String> getValues() {
        return this.values;
    }

    public static Manufacturer parse(JsonObject e) {

        Map<DataField, String> values = new LinkedHashMap<>();

        for (ManufacturerFields field : ManufacturerFields.values()) {
            if(e.has(field.getKey())) {
                values.put(field, e.get(field.getKey()).getAsString());
            }
        }

        return new Manufacturer(values);

    }

}
