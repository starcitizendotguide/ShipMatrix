package de.sweetode.ShipMatrix.diff;

import com.google.gson.JsonObject;
import de.sweetode.ShipMatrix.diff.report.DiffReport;
import de.sweetode.ShipMatrix.diff.types.DataField;
import de.sweetode.ShipMatrix.diff.types.DataFields;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledData;

import java.util.*;

public class Ship {

    private final String name;

    private final Manufacturer manufacturer;
    private final Map<DataField, String> values;
    private final CompiledData compiledData;

    public Ship(Map<DataField, String> values, Manufacturer manufacturer, CompiledData compiledData) {
        this.name = values.get(DataFields.NAME);
        this.values = values;
        this.manufacturer = manufacturer;
        this.compiledData = compiledData;
    }

    public String getName() {
        return name;
    }

    public Map<DataField, String> getValues() {
        return this.values;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public CompiledData getCompiledData() {
        return this.compiledData;
    }

    public static Ship parse(JsonObject e) {

        //--- Ship Fields
        Map<DataField, String> values = new LinkedHashMap<>();
        for (DataFields field : DataFields.values()) {
            if(e.has(field.getKey())) {
                values.put(field, e.get(field.getKey()).getAsString());
            }
        }

        //--- Manufacturer
        Manufacturer manufacturer = Manufacturer.parse(e.getAsJsonObject("manufacturer"));

        //--- CompiledData
        CompiledData compiledData = CompiledData.parse(e.getAsJsonObject("compiled"));

        return new Ship(values, manufacturer, compiledData);

    }

    @Override
    public String toString() {
        return this.getValues().get(DataFields.ID);
    }

}
