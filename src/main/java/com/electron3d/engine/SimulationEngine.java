package com.electron3d.engine;

import com.electron3d.model.Simulation;

public class SimulationEngine {
    private int speed;
    private int maxTimer;
    private int timer;
    private Simulation simulation;

    public SimulationEngine(Simulation simulation, int speed, int maxTimer) {
        this.simulation = simulation;
        this.speed = speed;
        this.maxTimer = maxTimer;
    }

    public void start() {
        simulation.init();

        while (timer != maxTimer * 60) {
            System.out.println("Day: " + ++timer);
            simulation.simulate();
            try {
                Thread.sleep(10000 / speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Time is over! Final results:");
        System.out.println("Results are printing");
        simulation.printSimulationResults();
    }
}
