package de.sweetode.ShipMatrix.diff.report;

import de.sweetode.ShipMatrix.diff.types.DiffTypes;

public class DiffEntry<T> {

    private T dataType;
    private DiffTypes diffType;
    private String original;
    private String changed;

    public DiffEntry(T dataType, DiffTypes diffType, String original, String changed) {
        this.dataType = dataType;
        this.diffType = diffType;
        this.original = original;
        this.changed = changed;
    }

    public T getDataType() {
        return this.dataType;
    }

    public DiffTypes getDiffType() {
        return this.diffType;
    }

    public String getOriginal() {
        return this.original;
    }

    public String getChanged() {
        return this.changed;
    }
}
