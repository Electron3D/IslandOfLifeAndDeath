package com.electron3d.model;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.island.Island;

import java.util.List;

public class IslandSimulation implements Simulation {
    private Island island;

    @Override
    public void init() {
        System.out.println("Loading configuration...");
        AnimalsConfig animalsConfig = AnimalsConfig.getInstance();
        List<String> animalTypes = animalsConfig.getAnimalsTypes();
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        System.out.println("Configuration loaded");
        System.out.println("Creating an island...");
        island = new Island(
                islandSimulationConfig.getIslandDimensions().getXDimension(),
                islandSimulationConfig.getIslandDimensions().getYDimension(),
                animalTypes);
        System.out.println("The island created");
        System.out.println("Initialization...");
        island.initFields();
        System.out.println("Initialization complete!");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void simulate() {
        island.live();
        System.out.println("Results are printing");
        printSimulationResults();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void printSimulationResults() {
        System.out.println(island);
        island.printStats();
    }
}
