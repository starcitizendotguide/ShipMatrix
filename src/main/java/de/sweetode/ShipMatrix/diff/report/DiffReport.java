package de.sweetode.ShipMatrix.diff.report;

import de.sweetode.ShipMatrix.diff.types.DiffTypes;
import de.sweetode.ShipMatrix.diff.types.DataField;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledDiffEntry;
import de.sweetode.ShipMatrix.diff.types.compiled.CompiledFields;
import de.sweetode.ShipMatrix.diff.types.compiled.ParentTypes;
import de.sweetode.ShipMatrix.diff.types.component.ComponentTypeFields;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DiffReport {

    private DiffTypes totalShipStatus = null;
    private List<DiffEntry<DataField>> shipFieldChanges = new LinkedList<>();
    private Map<ParentTypes, Map<ComponentTypeFields, Map<Integer, List<CompiledDiffEntry>>>> compiledChanges = new LinkedHashMap<>();

    public DiffReport() {}

    public boolean detectedChanges() {
        return (
                    !(this.getTotalShipStatus() == null) ||
                    !this.getShipFieldChanges().isEmpty() ||
                    !this.getCompiledChanges().isEmpty()
                );
    }

    public DiffTypes getTotalShipStatus() {
        return totalShipStatus;
    }

    public List<DiffEntry<DataField>> getShipFieldChanges() {
        return shipFieldChanges;
    }

    public Map<ParentTypes, Map<ComponentTypeFields, Map<Integer, List<CompiledDiffEntry>>>> getCompiledChanges() {
        return compiledChanges;
    }

    public void addShipChange(DiffEntry<DataField> entry) {
        this.shipFieldChanges.add(entry);
    }

    public void setTotalShipStatus(DiffTypes totalShipStatus) {
        this.totalShipStatus = totalShipStatus;
    }

    public void addCompiledChange(ParentTypes parentType, ComponentTypeFields childType, int localId, CompiledDiffEntry diffEntry) {

        if(!(this.compiledChanges.containsKey(parentType))) {
            this.compiledChanges.put(parentType, new LinkedHashMap<>());
        }

        if(!(this.compiledChanges.get(parentType).containsKey(childType))) {
            this.compiledChanges.get(parentType).put(childType, new LinkedHashMap<>());
        }

        if(!(this.compiledChanges.get(parentType).get(childType).containsKey(localId))) {
            this.compiledChanges.get(parentType).get(childType).put(localId, new LinkedList<>());
        }

        this.compiledChanges.get(parentType).get(childType).get(localId).add(diffEntry);

    }

}
