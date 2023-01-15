package com.electron3d;

import com.electron3d.engine.SimulationEngine;
import com.electron3d.model.IslandSimulation;

public class Main {
    public static void main(String[] args) {
        SimulationConfigBuilder builder = new SimulationConfigBuilder();
        builder.built();
        SimulationEngine engine = new SimulationEngine(new IslandSimulation(), 100, 1);
        engine.start();
    }
}