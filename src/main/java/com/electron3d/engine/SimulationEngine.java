package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;

public class SimulationEngine {
    private final IslandSimulation simulation;
    private final int timeMultiplier;
    private final int maxTimeOfSimulationInSeconds;
    private int timer;

    public SimulationEngine(IslandSimulation simulation, int timeMultiplier, int maxTimeOfSimulationInSeconds) {
        this.simulation = simulation;
        this.timeMultiplier = timeMultiplier;
        this.maxTimeOfSimulationInSeconds = maxTimeOfSimulationInSeconds;
    }

    public void start() {
        Renderer renderer = new Renderer(simulation);
        simulation.init();
        System.out.println("Starting condition:");
        renderer.printStartSimulationConditions();
        System.out.println();
        System.out.println();
        System.out.println();

        while (timer != maxTimeOfSimulationInSeconds) {
            System.out.println("Day: " + ++timer);
            simulation.simulate();
            try {
                Thread.sleep(1000 / timeMultiplier);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Time is over! Final results:");
        simulation.printSimulationResults();
    }
}
