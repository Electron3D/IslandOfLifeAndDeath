package com.electron3d;

import com.electron3d.engine.SimulationEngine;
import com.electron3d.model.IslandSimulation;

public class Main {
    public static void main(String[] args) {
        SimulationEngine engine = new SimulationEngine(new IslandSimulation(), 100, 1); //todo add parameters from config
        engine.start();
    }
}