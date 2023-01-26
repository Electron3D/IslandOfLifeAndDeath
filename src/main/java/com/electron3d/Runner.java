package com.electron3d;

import com.electron3d.engine.SimulationEngine;
import com.electron3d.model.IslandSimulation;
import com.electron3d.model.config.IslandSimulationConfig;

public class Runner {
    public static void main(String[] args) {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        SimulationEngine engine = new SimulationEngine(
                new IslandSimulation(),
                islandSimulationConfig.getTimeDelayMultiplier(),
                islandSimulationConfig.getMaxTimeOfSimulationInSeconds());
        engine.start();
    }
}