package com.electron3d.model;

import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.island.Island;

import java.util.Arrays;

public class IslandSimulation {
    private Island island;

    public void init() {
        System.out.println("Creating an island...");
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        island = new Island(
                islandSimulationConfig.getxDimension(),
                islandSimulationConfig.getyDimension(),
                Arrays.stream(AnimalType.values()).toList());
        System.out.println("The island created");
        System.out.println("Initialization...");
        island.initCells();
        System.out.println("Initialization complete!\n");
    }

    public boolean simulateADay() {
        return island.liveADay();
    }

    public Island getIsland() {
        return island;
    }
}
