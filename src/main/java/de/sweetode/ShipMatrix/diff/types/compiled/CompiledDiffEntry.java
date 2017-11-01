package de.sweetode.ShipMatrix.diff.types.compiled;

import de.sweetode.ShipMatrix.diff.report.DiffEntry;
import de.sweetode.ShipMatrix.diff.types.DiffTypes;

public class CompiledDiffEntry extends DiffEntry<CompiledDiffEntry.Impact> {

    //--- Impact: PARENT
    private String parent;

    //--- Impact: COMPONENT_TYPE
    private String componentType;

    //--- Impact: COMPONENT
    private String componentName;
    private CompiledFields field;

    public CompiledDiffEntry(CompiledFields field, DiffTypes diffType, String original, String changed) {
        super(Impact.COMPONENT_DATA, diffType, original, changed);
        this.field = field;
    }

    public CompiledDiffEntry(Impact impact, String name, DiffTypes diffType) {
        super(impact, diffType, null, null);
        if(impact == Impact.COMPONENT_DATA) {
            throw new IllegalStateException();
        }

        switch (impact) {

            case PARENT: this.parent = name; break;
            case COMPONENT_TYPE: this.componentType = name; break;
            case COMPONENT: this.componentName = name; break;

            default: new IllegalArgumentException();

        }
    }

    public CompiledFields getField() {
        return field;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getParent() {
        return parent;
    }

    public enum Impact {

        PARENT,
        COMPONENT_TYPE,
        COMPONENT,
        COMPONENT_DATA

    }

}
