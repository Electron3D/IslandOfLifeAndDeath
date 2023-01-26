package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;

public class SimulationEngine {
    private final IslandSimulation simulation;
    private final double timeDelayMultiplier;
    private final int maxTimeOfSimulationInSeconds;
    private int timer;

    public SimulationEngine(IslandSimulation simulation,
                            double timeDelayMultiplier,
                            int maxTimeOfSimulationInSeconds) {
        this.simulation = simulation;
        this.timeDelayMultiplier = timeDelayMultiplier;
        this.maxTimeOfSimulationInSeconds = maxTimeOfSimulationInSeconds;
    }

    public void start() {
        simulation.init();
        Renderer renderer = new Renderer(simulation);
        renderer.printStartSimulationConditions();

        while (timer != maxTimeOfSimulationInSeconds) {
            timer++;
            renderer.printSimulationStateForDay(timer);
            boolean isSimulationEnded = simulation.simulateADay();
            if (isSimulationEnded) {
                break;
            }
            try {
                Thread.sleep((long) (1000L * timeDelayMultiplier));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        renderer.printSimulationResults(timer);
    }
}
