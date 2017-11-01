package de.sweetode.ShipMatrix.diff.types;

public enum DataFields implements DataField {

    ID {
        @Override
        public String getName() {
            return "ID";
        }

        @Override
        public String getKey() {
            return "id";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    PRODUCTION_STATUS {
        @Override
        public String getName() {
            return "Production Status";
        }

        @Override
        public String getKey() {
            return "production_status";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    LENGTH {
        @Override
        public String getName() {
            return "Length";
        }

        @Override
        public String getKey() {
            return "length";
        }

        @Override
        public String getUnit() {
            return "m";
        }
    },
    BEAM {
        @Override
        public String getName() {
            return "Beam";
        }

        @Override
        public String getKey() {
            return "beam";
        }

        @Override
        public String getUnit() {
            return "m";
        }
    },
    HEIGHT {
        @Override
        public String getName() {
            return "Height";
        }

        @Override
        public String getKey() {
            return "height";
        }

        @Override
        public String getUnit() {
            return "m";
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
    MASS {
        @Override
        public String getName() {
            return "Mass";
        }

        @Override
        public String getKey() {
            return "mass";
        }

        @Override
        public String getUnit() {
            return "m";
        }
    },
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
    MIN_CREW {
        @Override
        public String getName() {
            return "Min. Crew";
        }

        @Override
        public String getKey() {
            return "min_crew";
        }

        @Override
        public String getUnit() {
            return "persons";
        }
    },
    MAX_CREW {
        @Override
        public String getName() {
            return "Max. Crew";
        }

        @Override
        public String getKey() {
            return "max_crew";
        }

        @Override
        public String getUnit() {
            return "persons";
        }
    },
    SCM_SPEED {
        @Override
        public String getName() {
            return "SCM Speed";
        }

        @Override
        public String getKey() {
            return "scm_speed";
        }

        @Override
        public String getUnit() {
            return "m/s";
        }
    },
    AFTERBURNER_SPEED {
        @Override
        public String getName() {
            return "Afterburner Speed";
        }

        @Override
        public String getKey() {
            return "afterburner_speed";
        }

        @Override
        public String getUnit() {
            return "m/s";
        }
    },
    PITCH_MAX {
        @Override
        public String getName() {
            return "Max. Pitch";
        }

        @Override
        public String getKey() {
            return "pitch_max";
        }

        @Override
        public String getUnit() {
            return "deg/s";
        }
    },
    YAW_MAX {
        @Override
        public String getName() {
            return "Max. Yaw";
        }

        @Override
        public String getKey() {
            return "yaw_max";
        }

        @Override
        public String getUnit() {
            return "deg/s";
        }
    },
    ROLL_MAX {
        @Override
        public String getName() {
            return "Max. Roll";
        }

        @Override
        public String getKey() {
            return "roll_max";
        }

        @Override
        public String getUnit() {
            return "deg/s";
        }
    },
    XAXIS_ACCELERATION {
        @Override
        public String getName() {
            return "X-Axis Acceleration";
        }

        @Override
        public String getKey() {
            return "xaxis_acceleration";
        }

        @Override
        public String getUnit() {
            return "m/s";
        }
    },
    YAXIS_ACCELERATION {
        @Override
        public String getName() {
            return "Y-Axis Acceleration";
        }

        @Override
        public String getKey() {
            return "yaxis_acceleration";
        }

        @Override
        public String getUnit() {
            return "m/s";
        }
    },
    ZAXIS_ACCELERATION {
        @Override
        public String getName() {
            return "Z-Axis Acceleration";
        }

        @Override
        public String getKey() {
            return "zaxis_acceleration";
        }

        @Override
        public String getUnit() {
            return "m/s";
        }
    },
    MANUFACTURER_ID {
        @Override
        public String getName() {
            return "Manufacturer ID";
        }

        @Override
        public String getKey() {
            return "manufacturer_id";
        }
    },
    CHASSIS_ID {
        @Override
        public String getName() {
            return "Chassis ID";
        }

        @Override
        public String getKey() {
            return "chassis_id";
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
    FOCUS {
        @Override
        public String getName() {
            return "Focus";
        }

        @Override
        public String getKey() {
            return "focus";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    DESCRIPTION {
        @Override
        public String getName() {
            return "Description";
        }

        @Override
        public String getKey() {
            return "description";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    URL {
        @Override
        public String getName() {
            return "URL";
        }

        @Override
        public String getKey() {
            return "url";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },

}
