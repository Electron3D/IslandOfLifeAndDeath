package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;

public class SimulationEngine {
    private final IslandSimulation simulation;
    private final int timeFlowSpeedMultiplier;
    private final int maxTimeOfSimulationInSeconds;
    private int timer;

    public SimulationEngine(IslandSimulation simulation, int timeFlowSpeedMultiplier, int maxTimeOfSimulationInSeconds) {
        this.simulation = simulation;
        this.timeFlowSpeedMultiplier = timeFlowSpeedMultiplier;
        this.maxTimeOfSimulationInSeconds = maxTimeOfSimulationInSeconds;
    }

    public void start() {
        simulation.init();
        Renderer renderer = new Renderer(simulation);
        renderer.printStartSimulationConditions();

        while (timer != maxTimeOfSimulationInSeconds) {
            timer++;
            renderer.printCurrentDay(timer);
            boolean isSimulationEnded = simulation.simulate();
            if (isSimulationEnded) {
                break;
            }
            try {
                Thread.sleep(1000 / timeFlowSpeedMultiplier);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        renderer.printSimulationResults(timer);
    }
}
