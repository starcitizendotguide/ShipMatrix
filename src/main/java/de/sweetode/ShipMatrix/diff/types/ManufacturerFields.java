package de.sweetode.ShipMatrix.diff.types;

public enum  ManufacturerFields implements DataField {

    ID {
        @Override
        public String getName() {
            return "ID";
        }

        @Override
        public String getKey() {
            return "id";
        }
    }

}
