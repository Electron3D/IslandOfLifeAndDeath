package com.electron3d.model;

import com.electron3d.model.island.Island;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IslandSimulation implements Simulation {
    private Island island;

    @Override
    public void init() {
        System.out.println("Loading configuration...");
        Set<String> animalTypes = new HashSet<>(List.of("Horse", "Deer", "Duck", "Wolf", "Bear"));   //todo, temporary hardcoded
        System.out.println("Configuration loaded");
        System.out.println("Creating an island...");
        island = new Island(10, 10, animalTypes); ////todo, temporary hardcoded
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
