package com.electron3d.model.config;

import java.util.HashSet;
import java.util.Set;

public class IslandConfig implements Config {
    private static IslandConfig INSTANCE;
    public static IslandConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IslandConfig();
        }
        return INSTANCE;
    }

    private IslandConfig() {
    }

    public Set<String> getAnimalTypes() {
        return new HashSet<>();
    }
    public int getBoundForType (String type) {
        return 10;
    }
}
