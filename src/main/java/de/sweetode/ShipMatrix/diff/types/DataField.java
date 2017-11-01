package de.sweetode.ShipMatrix.diff.types;

public interface DataField {

    String getName();

    String getKey();

    default String getUnit() {
        return "<>";
    }

}
