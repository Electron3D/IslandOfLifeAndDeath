package com.electron3d.model;

import com.electron3d.model.island.Island;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IslandSimulation implements Simulation{
    private Island island;

    @Override
    public void init() {
        System.out.println("Initialization...");
        Set<String> animalsSpecies = new HashSet<>(List.of("Horse"));   //temporary hardcoded
        island = new Island(10, 10, animalsSpecies);
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

        System.out.println("Animals are walking");
        System.out.println("Dinner time!");

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
