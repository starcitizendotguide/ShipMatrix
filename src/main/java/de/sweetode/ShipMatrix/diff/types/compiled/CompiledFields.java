package de.sweetode.ShipMatrix.diff.types.compiled;

import de.sweetode.ShipMatrix.diff.types.DataField;

import java.util.stream.Stream;

public enum CompiledFields implements DataField {

    TYPE {
        @Override
        public String getName() {
            return "Type";
        }

        @Override
        public String getKey() {
            return "type";
        }
    },
    NAME {
        @Override
        public String getName() {
            return "Name";
        }

        @Override
        public String getKey() {
            return "name";
        }
    },
    MOUNTS {
        @Override
        public String getName() {
            return "Mounts";
        }

        @Override
        public String getKey() {
            return "mounts";
        }
    },
    COMPONENT_SIZE {
        @Override
        public String getName() {
            return "Component Size";
        }

        @Override
        public String getKey() {
            return "component_size";
        }
    },
    SIZE {
        @Override
        public String getName() {
            return "Size";
        }

        @Override
        public String getKey() {
            return "size";
        }
    },
    DETAILS {
        @Override
        public String getName() {
            return "Details";
        }

        @Override
        public String getKey() {
            return "details";
        }
    },
    QUANTITY {
        @Override
        public String getName() {
            return "Quantity";
        }

        @Override
        public String getKey() {
            return "quantity";
        }
    },
    MANUFACTURER {
        @Override
        public String getName() {
            return "Manufacturer";
        }

        @Override
        public String getKey() {
            return "manufacturer";
        }
    },
    CATEGORY {
        @Override
        public String getName() {
            return "Category";
        }

        @Override
        public String getKey() {
            return "category";
        }
    };

}

