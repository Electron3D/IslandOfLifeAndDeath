package com.electron3d;

import com.electron3d.engine.SimulationEngine;
import com.electron3d.model.IslandSimulation;
import com.electron3d.model.config.SimulationConfig;

public class Main {
    public static void main(String[] args) {
        SimulationConfig simulationConfig = SimulationConfig.getInstance();
        SimulationEngine engine = new SimulationEngine(new IslandSimulation(), simulationConfig.getSpeedOfSimulation(), simulationConfig.getMaxTimer());
        engine.start();
    }
}