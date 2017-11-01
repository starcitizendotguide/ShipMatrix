package de.sweetode.ShipMatrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sweetode.ShipMatrix.diff.report.DiffEntry;
import de.sweetode.ShipMatrix.diff.report.DiffReport;
import de.sweetode.ShipMatrix.diff.Ship;
import de.sweetode.ShipMatrix.diff.types.DataFields;
import de.sweetode.ShipMatrix.diff.types.DiffTypes;
import de.sweetode.ShipMatrix.diff.types.DataField;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledDiffEntry;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledEntryContainer;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledFields;
import de.sweetode.ShipMatrix.diff.types.component.ComponentTypeFields;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CompareMatrix {

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final static DateFormat dateFormat = new SimpleDateFormat("d-MM-y");

    public CompareMatrix() { }

    public Map<String, Map<Ship, DiffReport>> diffAll() throws IOException {

        Map<String, Map<Ship, DiffReport>> diff_map = new LinkedHashMap<>();

        //--- Loading & Sorting Files
        File dir = new File("data");
        File[] fileList = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".json"));
        assert fileList != null && !(fileList.length == 0);

        List<File> sorted_files = Arrays.asList(fileList);
        sorted_files.sort(CompareMatrix::compare);

        //--- Diff
        File pre = sorted_files.get(0);

        for(int i = 1; i < sorted_files.size(); i++) {

            File compare_file = sorted_files.get(i);
            Map<Ship, DiffReport> local_diff = diff_(pre, compare_file);

            if(!local_diff.isEmpty()) {
                diff_map.put(compare_file.getName(), local_diff);
            }

            pre = compare_file;

        }


        //--- Reverse Map
        List<Map.Entry<String, Map<Ship, DiffReport>>> list = new ArrayList<>(diff_map.entrySet());
        Map<String, Map<Ship, DiffReport>> reversed = new LinkedHashMap<>();
        for(int i = list.size() - 1; i >= 0 ; i --){
            reversed.put(list.get(i).getKey(), list.get(i).getValue());
        }

        return reversed;

    }

    public Map<Ship, DiffReport> diff_(File file_a, File file_b) throws IOException {
        JsonArray array_a = gson.fromJson(FileUtils.readFileToString(file_a, Charset.forName("UTF-8")), JsonArray.class);
        JsonArray array_b = gson.fromJson(FileUtils.readFileToString(file_b, Charset.forName("UTF-8")), JsonArray.class);

        Map<Integer, Ship> store_a = new HashMap<>();
        array_a.forEach(e -> {
            //--- Prepare
            JsonObject element = e.getAsJsonObject();
            int id = Integer.valueOf(element.get("id").getAsString());
            element.remove("time_modified");

            store_a.put(id, Ship.parse(element));
        });

        Map<Integer, Ship> store_b = new HashMap<>();
        array_b.forEach(e -> {
            //--- Prepare
            JsonObject element = e.getAsJsonObject();
            int id = Integer.valueOf(element.get("id").getAsString());
            element.remove("time_modified");

            store_b.put(id, Ship.parse(element));
        });

        Map<Ship, DiffReport> diffReportMap = new LinkedHashMap<>();

        store_a.forEach((id, e) -> {

            DiffReport report = new DiffReport();
            diffReportMap.put(e, report);

            Ship compareShip = store_b.get(id);

            //--- Ship got removed!
            if(compareShip == null) {
                report.setTotalShipStatus(DiffTypes.REMOVED);
            }
            //--- Check if field changed
            else {

                //--- DataFields
                Map<DataField, String> df_values_a = e.getValues();
                Map<DataField, String> df_values_b = compareShip.getValues();

                df_values_a.forEach((k, value_a) -> {

                    String value_b = df_values_b.get(k);

                    //--- Value removed
                    if(value_b == null) {
                        report.addShipChange(new DiffEntry<>(k, DiffTypes.REMOVED, value_a, null));
                    } else if(!(value_a.equals(value_b))) {
                        report.addShipChange(new DiffEntry<>(k, DiffTypes.MODIFIED, value_a, value_b));
                    }

                });

                //--- CompiledData
                Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> cd_values_a = e.getCompiledData().getFields();
                Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> cd_values_b = compareShip.getCompiledData().getFields();

                //--- Clean
                cd_values_a.forEach((parent, parents_a) -> parents_a.forEach((key, list) -> {
                    Iterator<CompiledEntryContainer> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        CompiledEntryContainer item = iterator.next();
                        if(cd_values_b.containsKey(parent) && cd_values_b.get(parent).containsKey(key) && cd_values_b.get(parent).get(key).contains(item)) {
                            Iterator<CompiledEntryContainer> iterator_b = cd_values_b.get(parent).get(key).iterator();
                            boolean done = false;
                            while (iterator_b.hasNext() && !done) {
                                if(iterator_b.next().equals(item)) {
                                    iterator_b.remove();
                                    done = true;
                                }
                            }
                            if(done) {
                                iterator.remove();
                            }
                        }
                    }
                }));

                //--- COMPILED-PARENT
                cd_values_a.forEach((parent, parents_a) -> {

                    Map<ComponentTypeFields, List<CompiledEntryContainer>> components_b = cd_values_b.get(parent);

                    //--- COMPILED-COMPONENT-TYPE
                    parents_a.forEach((componentType, type_list_a) -> {

                        if(components_b.containsKey(componentType)) {

                            //--- COMPILED-COMPONENT
                            List<CompiledEntryContainer> type_list_b = components_b.get(componentType);
                            for (int i = 0; i < type_list_a.size(); i++) {

                                CompiledEntryContainer component_a = type_list_a.get(i);

                                //--- Check if the component changed
                                if(type_list_b.size() > i) {
                                    CompiledEntryContainer component_b = type_list_b.get(i);

                                    Map<CompiledFields, String> dataPointValues_a = component_a.getValues();
                                    Map<CompiledFields, String> dataPointValues_b = component_b.getValues();


                                    //--- CompiledFields
                                    int finalI = i;
                                    dataPointValues_a.forEach((field_a, value_a) -> {

                                        //--- COMPONENT DATA Removed
                                        if(!(dataPointValues_b.containsKey(field_a))) {
                                            report.addCompiledChange(parent, componentType, finalI, new CompiledDiffEntry(field_a, DiffTypes.REMOVED, value_a, null));
                                        }
                                        //--- COMPONENT DATA Changed
                                        else if(!(value_a.equals(dataPointValues_b.get(field_a)))) {
                                            report.addCompiledChange(parent, componentType, finalI, new CompiledDiffEntry(field_a, DiffTypes.MODIFIED, value_a, dataPointValues_b.get(field_a)));
                                        }

                                    });


                                }
                                //--- COMPONENT: Removed
                                else {
                                    report.addCompiledChange(parent, componentType, i, new CompiledDiffEntry(CompiledDiffEntry.Impact.COMPONENT, component_a.getValues().getOrDefault(CompiledFields.NAME, "<Unknown>"), DiffTypes.REMOVED));
                                }

                            }
                            //--- COMPILED-COMPONENT END

                        }
                        //--- COMPONENT-TYPE removed
                        else {
                            report.addCompiledChange(parent, componentType, -1, new CompiledDiffEntry(CompiledDiffEntry.Impact.COMPONENT_TYPE, componentType.getName(), DiffTypes.REMOVED));
                        }

                    });

                });

            }

        });


        store_b.forEach((Integer id, Ship e) -> {

            Ship compareShip = store_a.get(id);

            //--- Ship got added!
            if(compareShip == null) {
                DiffReport report = new DiffReport();
                diffReportMap.put(e, report);
                report.setTotalShipStatus(DiffTypes.ADDED);
            }
            //--- Check if field changed
            else {

                //--- DataFields
                Map<DataField, String> values_b = e.getValues();
                Map<DataField, String> values_a = compareShip.getValues();

                //@TODO Why does the normal Map#get not work?
                DiffReport report = diffReportMap.entrySet().stream().filter((_entry) -> _entry.getKey().getValues().get(DataFields.ID).equals(id.toString())).findAny().get().getValue();

                if(report == null) {
                    throw new IllegalArgumentException("REPORT NULL");
                }

                values_b.forEach((k, value_b) -> {

                    String value_a = values_a.get(k);

                    if(value_a == null) {
                        report.addShipChange(new DiffEntry<>(k, DiffTypes.ADDED, null, value_b));
                    }

                });

                //--- CompiledData
                Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> cd_values_b = e.getCompiledData().getFields();
                Map<String, Map<ComponentTypeFields, List<CompiledEntryContainer>>> cd_values_a = compareShip.getCompiledData().getFields();

                //--- COMPILED-PARENT
                cd_values_b.forEach((parent, parents_b) -> {

                    Map<ComponentTypeFields, List<CompiledEntryContainer>> components_a = cd_values_a.get(parent);

                    //--- COMPILED-COMPONENT-TYPE
                    parents_b.forEach((componentType, type_list_b) -> {

                        if(components_a.containsKey(componentType)) {

                            //--- COMPILED-COMPONENT
                            List<CompiledEntryContainer> type_list_a = components_a.get(componentType);
                            for (int i = 0; i < type_list_b.size(); i++) {

                                //--- Check if a completely new component was added
                                if(type_list_a.size() <= i) {
                                    report.addCompiledChange(parent, componentType, i, new CompiledDiffEntry(CompiledDiffEntry.Impact.COMPONENT, type_list_b.get(i).getValues().getOrDefault(CompiledFields.NAME, "<Unknown>"), DiffTypes.ADDED));
                                }
                                //--- Check for new Fields
                                else {

                                    CompiledEntryContainer component_b = type_list_b.get(i);
                                    CompiledEntryContainer component_a = type_list_b.get(i);

                                    Map<CompiledFields, String> dataPointValues_b = component_b.getValues();
                                    Map<CompiledFields, String> dataPointValues_a = component_a.getValues();


                                    //--- CompiledFields
                                    int finalI = i;
                                    dataPointValues_b.forEach((field_b, value_b) -> {

                                        //--- ADDED
                                        if (!(dataPointValues_a.containsKey(field_b))) {
                                            report.addCompiledChange(parent, componentType, finalI, new CompiledDiffEntry(field_b, DiffTypes.ADDED, null, value_b));
                                        }

                                    });
                                }

                            }
                            //--- COMPILED-COMPONENT END
                        }
                        //--- COMPONENT-TYPE Added
                        else {
                            report.addCompiledChange(parent, componentType, -1, new CompiledDiffEntry(CompiledDiffEntry.Impact.COMPONENT_TYPE, componentType.getName(), DiffTypes.ADDED));
                        }

                    });

                });

            }

        });

        return diffReportMap;
    }

    private static int compare(File a, File b) {
        String[] tmp_a = a.getName().split(File.pathSeparator);
        String[] tmp_b = b.getName().split(File.pathSeparator);

        String date_string_a = tmp_a[tmp_a.length - 1];
        String date_string_b = tmp_b[tmp_b.length - 1];

        try {
            Date date_a = dateFormat.parse(date_string_a);
            Date date_b = dateFormat.parse(date_string_b);

            return date_a.compareTo(date_b);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException();
    }

}
