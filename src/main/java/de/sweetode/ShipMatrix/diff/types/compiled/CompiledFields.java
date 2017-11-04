package de.sweetode.ShipMatrix.diff.types.compiled;

import de.sweetode.ShipMatrix.diff.types.DataField;

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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
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

        @Override
        public String getUnit() {
            return "";
        }
    };


}

