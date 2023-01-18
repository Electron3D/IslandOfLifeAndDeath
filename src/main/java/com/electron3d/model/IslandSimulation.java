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
                islandSimulationConfig.getIslandLength().getParallelLength(),
                islandSimulationConfig.getIslandLength().getMeridianLength(),
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
        System.out.println("Plants are growing");
        island.growPlants();

        System.out.println("Animals living");
        island.doAnimalStuff();

        System.out.println("Results are printing");
        printSimulationResults();

        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void printSimulationResults() {
        System.out.println(island);
        System.out.println(island.getPlantsPull().size());
        System.out.println(island.getAnimalsPull().size());
    }
}
