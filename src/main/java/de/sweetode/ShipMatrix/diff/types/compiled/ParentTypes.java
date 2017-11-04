package de.sweetode.ShipMatrix.diff.types.compiled;

import de.sweetode.ShipMatrix.diff.types.DataField;

import java.util.Optional;
import java.util.stream.Stream;

public enum ParentTypes implements DataField {

    RSI_WEAPON {
        @Override
        public String getName() {
            return "Weapons";
        }

        @Override
        public String getKey() {
            return "RSIWeapon";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    RSI_AVIONIC {
        @Override
        public String getName() {
            return "Avionics";
        }

        @Override
        public String getKey() {
            return "RSIAvionic";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    RSI_PROPULSION {
        @Override
        public String getName() {
            return "Propulsion";
        }

        @Override
        public String getKey() {
            return "RSIPropulsion";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    RSI_THRUSTER {
        @Override
        public String getName() {
            return "Thrusters";
        }

        @Override
        public String getKey() {
            return "RSIThruster";
        }

        @Override
        public String getUnit() {
            return "";
        }
    },
    RSI_MODULAR {
        @Override
        public String getName() {
            return "Modules";
        }

        @Override
        public String getKey() {
            return "RSIModular";
        }

        @Override
        public String getUnit() {
            return "";
        }
    };

    public static ParentTypes byKey(String key) {
        Optional<ParentTypes> t = Stream.of(ParentTypes.values()).filter(e -> e.getKey().equals(key)).findAny();

        if(!(t.isPresent())) {
            throw new IllegalStateException(String.format("Add the key \"%s\" to ParentTypes.", key));
        }

        return t.get();
    }

}
