package com.electron3d.model;

import java.util.HashSet;
import java.util.Set;

public class IslandSimulationConfig implements SimulationConfig {
    private static IslandSimulationConfig INSTANCE;
    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IslandSimulationConfig();
        }
        return INSTANCE;
    }

    private IslandSimulationConfig() {
    }

    public Set<String> getAnimalTypes() {
        return new HashSet<>();
    }
    public int getBoundForType (String type) {
        return 10;
    }
}
