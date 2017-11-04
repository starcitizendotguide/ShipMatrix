package de.sweetode.ShipMatrix.diff.types.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import de.sweetode.ShipMatrix.diff.types.DataField;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

public enum ComponentTypeFields implements DataField {

    WEAPONS {
        @Override
        public String getName() {
            return "Weapons";
        }

        @Override
        public String getKey() {
            return "weapons";
        }
    },
    POWER_PLANTS {
        @Override
        public String getName() {
            return "Power Plants";
        }

        @Override
        public String getKey() {
            return "power_plants";
        }
    },
    SHIELD_GENERATORS {
        @Override
        public String getName() {
            return "Shield Generators";
        }

        @Override
        public String getKey() {
            return "shield_generators";
        }
    },
    UTILITY_ITEMS {
        @Override
        public String getName() {
            return "Utility Items";
        }

        @Override
        public String getKey() {
            return "utility_items";
        }
    },
    MISSILES {
        @Override
        public String getName() {
            return "Missiles";
        }

        @Override
        public String getKey() {
            return "missiles";
        }
    },
    TURRETS {
        @Override
        public String getName() {
            return "Turrets";
        }

        @Override
        public String getKey() {
            return "turrets";
        }
    },
    MAIN_THRUSTERS {
        @Override
        public String getName() {
            return "Main Thrusters";
        }

        @Override
        public String getKey() {
            return "main_thrusters";
        }
    },
    MANEUVERING_THRUSTERS {
        @Override
        public String getName() {
            return "Maneuvering Thrusters";
        }

        @Override
        public String getKey() {
            return "maneuvering_thrusters";
        }
    },
    RADAR {
        @Override
        public String getName() {
            return "Radar";
        }

        @Override
        public String getKey() {
            return "radar";
        }
    },
    COMPUTERS {
        @Override
        public String getName() {
            return "Computers";
        }

        @Override
        public String getKey() {
            return "computers";
        }
    },
    FUEL_INTAKES {
        @Override
        public String getName() {
            return "Fuel Intakes";
        }

        @Override
        public String getKey() {
            return "fuel_intakes";
        }
    },
    FUEL_TANKS {
        @Override
        public String getName() {
            return "Fuel Tanks";
        }

        @Override
        public String getKey() {
            return "fuel_tanks";
        }
    },
    QUANTUM_DRIVES {
        @Override
        public String getName() {
            return "Quantum Drives";
        }

        @Override
        public String getKey() {
            return "quantum_drives";
        }
    },
    JUMP_MODULES {
        @Override
        public String getName() {
            return "Jump Modules";
        }

        @Override
        public String getKey() {
            return "jump_modules";
        }
    },
    QUANTUM_FUEL_TANKS {
        @Override
        public String getName() {
            return "Quantum Fuel Tanks";
        }

        @Override
        public String getKey() {
            return "quantum_fuel_tanks";
        }
    },
    COOLERS {
        @Override
        public String getName() {
            return "Coolers";
        }

        @Override
        public String getKey() {
            return "coolers";
        }
    },
    COUNTERMEASURES {
        @Override
        public String getName() {
            return "Countermeasures";
        }

        @Override
        public String getKey() {
            return "countermeasures";
        }
    };


    public static ComponentTypeFields byKey(String key) {
        Optional<ComponentTypeFields> t = Stream.of(ComponentTypeFields.values()).filter(e -> e.getKey().equals(key)).findAny();

        if(!(t.isPresent())) {
            throw new IllegalStateException(String.format("Add the key \"%s\" to ComponentTypeFields.", key));
        }

        return t.get();
    }

}
