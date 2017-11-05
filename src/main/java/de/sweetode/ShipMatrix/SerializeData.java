package de.sweetode.ShipMatrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import de.sweetode.ShipMatrix.diff.Ship;
import de.sweetode.ShipMatrix.diff.report.DiffReport;
import de.sweetode.ShipMatrix.diff.types.DataFields;
import de.sweetode.ShipMatrix.diff.types.DiffTypes;
import de.sweetode.ShipMatrix.diff.types.ManufacturerFields;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledFields;
import de.sweetode.ShipMatrix.diff.types.compiled.ParentTypes;
import de.sweetode.ShipMatrix.diff.types.component.ComponentTypeFields;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Stream;

public class SerializeData {

    public static void main(String[] args) throws IOException {
        CompareMatrix compareMatrix = new CompareMatrix();
        SerializeData serializeData = new SerializeData(compareMatrix);
        serializeData.generate();
    }

    private final Gson gson = new GsonBuilder().create();
    private final CompareMatrix compareMatrix;
    private final Map<String, Map<Ship, DiffReport>> reports;

    public SerializeData(CompareMatrix compareMatrix) throws IOException {
        this.compareMatrix = compareMatrix;
        this.reports = compareMatrix.diffAll();
    }

    public void generate() throws IOException {

        File file = new File("E:\\web-workspace\\private\\StarCitizen\\MatrixChangelog\\src\\assets\\data");

        //--- Ships
        Map<Integer, Ship> ships = new HashMap<>();
        this.reports.values().forEach(value -> value.keySet().forEach(ship -> {
            ships.put(Integer.valueOf(ship.getValues().get(DataFields.ID)), ship);
        }));
        FileUtils.write(new File(String.format("%s\\ships.json", file.getAbsolutePath())), gson.toJson(ships), Charset.forName("UTF-8"));

        //--- Changelog
        List<SerializationEntry> entries = new LinkedList<>();
        this.reports.forEach((key, value) -> entries.add(new SerializationEntry(key, value)));
        FileUtils.write(new File(String.format("%s\\changelog.json", file.getAbsolutePath())), gson.toJson(entries), Charset.forName("UTF-8"));

        //--- DiffTypes
        FileUtils.write(new File(String.format("%s\\diffTypes.json", file.getAbsolutePath())), gson.toJson(DiffTypes.values()), Charset.forName("UTF-8"));

        //--- DataFields
        Map<String, JsonElement> dataFields = new LinkedHashMap<>();
        Stream.of(DataFields.values()).forEach(e -> dataFields.put(e.name(), e.serialize(e, null, null)));
        FileUtils.write(new File(String.format("%s\\dataFields.json", file.getAbsolutePath())), gson.toJson(dataFields), Charset.forName("UTF-8"));

        //--- ManufactureFields
        Map<String, JsonElement> manufacturerFields = new LinkedHashMap<>();
        Stream.of(ManufacturerFields.values()).forEach(e -> manufacturerFields.put(e.name(), e.serialize(e, null, null)));
        FileUtils.write(new File(String.format("%s\\manufactureFields.json", file.getAbsolutePath())), gson.toJson(manufacturerFields), Charset.forName("UTF-8"));

        //--- CompiledFields
        Map<String, JsonElement> compiledFields = new LinkedHashMap<>();
        Stream.of(CompiledFields.values()).forEach(e -> compiledFields.put(e.name(), e.serialize(e, null, null)));
        FileUtils.write(new File(String.format("%s\\compiledFields.json", file.getAbsolutePath())), gson.toJson(compiledFields), Charset.forName("UTF-8"));

        //--- ComponentTypeFields
        Map<String, JsonElement> componentTypeFields = new LinkedHashMap<>();
        Stream.of(ComponentTypeFields.values()).forEach(e -> componentTypeFields.put(e.name(), e.serialize(e, null, null)));
        FileUtils.write(new File(String.format("%s\\componentTypeFields.json", file.getAbsolutePath())), gson.toJson(componentTypeFields), Charset.forName("UTF-8"));

        //--- ComponentTypeFields
        Map<String, JsonElement> parentTypes = new LinkedHashMap<>();
        Stream.of(ParentTypes.values()).forEach(e -> parentTypes.put(e.name(), e.serialize(e, null, null)));
        FileUtils.write(new File(String.format("%s\\parentTypes.json", file.getAbsolutePath())), gson.toJson(parentTypes), Charset.forName("UTF-8"));


        //--- Ship History
        Map<Integer, Map<String, Ship>> shipHistory = new HashMap<>();
        this.reports.forEach((date, value) -> value.forEach((ship, report) -> {

            int id = Integer.valueOf(ship.getValues().get(DataFields.ID));

            if (!(shipHistory.containsKey(id))) {
                shipHistory.put(id, new LinkedHashMap<>());
                shipHistory.get(id).put("Now", ship);
                return;
            }

            if(!(report.detectedChanges())) {
                return;
            }

            shipHistory.get(id).put(date.substring(0, date.indexOf('.')), ship);

        }));
        FileUtils.write(new File(String.format("%s\\shipHistory.json", file.getAbsolutePath())), gson.toJson(shipHistory), Charset.forName("UTF-8"));

    }

    private class SerializationEntry {

        private String date;
        private List<DiffReportHolder> diffReports = new LinkedList<>();

        public SerializationEntry(String date, Map<Ship, DiffReport> list) {
            this.date = date.substring(0, date.indexOf('.'));
            list.entrySet().stream()
                    .filter(e -> {
                        DiffReport r = e.getValue();
                        return (!r.getCompiledChanges().isEmpty() || !r.getShipFieldChanges().isEmpty() || !(r.getTotalShipStatus() == null));
                    })
                    .forEach(e -> diffReports.add(new DiffReportHolder(Integer.valueOf(e.getKey().getValues().get(DataFields.ID)), e.getValue())));
        }

        private class DiffReportHolder {

            private int id;
            private DiffReport diffReport;

            public DiffReportHolder(int id, DiffReport diffReport) {
                this.id = id;
                this.diffReport = diffReport;
            }

        }

    }

}
