package com.electron3d.model;

import com.electron3d.model.island.Island;

public class IslandSimulation implements Simulation{
    private Island island;

    @Override
    public void init() {
        System.out.println("Initialization...");
        island = new Island(5, 5);
        island.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialization complete!");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void simulate() {
        System.out.println("Plants are growing");
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
    }
}
