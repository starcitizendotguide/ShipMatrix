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
    MASS {
        @Override
        public String getName() {
            return "Mass";
        }

        @Override
        public String getKey() {
            return "mass";
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
    },

}
