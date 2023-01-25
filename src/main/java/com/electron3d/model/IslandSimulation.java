package com.electron3d.model;

import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.island.Island;

import java.util.Arrays;

public class IslandSimulation {
    private Island island;

    public void init() {
        System.out.println("Loading configuration...");
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        System.out.println("Configuration loaded");
        System.out.println("Creating an island...");
        island = new Island(
                islandSimulationConfig.getIslandDimensions().getXDimension(),
                islandSimulationConfig.getIslandDimensions().getYDimension(),
                Arrays.stream(AnimalType.values()).toList());
        System.out.println("The island created");
        System.out.println("Initialization...");
        island.initCells();
        System.out.println("Initialization complete!\n");
    }

    public boolean simulate() {
        island.growPlants();
        return island.live();
    }

    public Island getIsland() {
        return island;
    }
}
