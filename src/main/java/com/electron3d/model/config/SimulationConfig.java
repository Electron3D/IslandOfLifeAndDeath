package com.electron3d.model.config;

public class SimulationConfig extends Config {
    //todo add started parameters for simulation such as island size, speed of simulation, etc...
    private static SimulationConfig INSTANCE;
    private int speedOfSimulation;
    private int maxTimer;
    public static SimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimulationConfigBuilder<>(new SimulationConfig()).buildAndGetConfig();
        }
        return INSTANCE;
    }

    private SimulationConfig() {
    }

    public int getSpeedOfSimulation() {
        return speedOfSimulation;
    }

    public int getMaxTimer() {
        return maxTimer;
    }

    public static class SimulationConfigBuilder<T extends SimulationConfig> extends ConfigBuilder<T> {
        //todo add more specific methods to builder
        private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.csv";

        public SimulationConfigBuilder(T simulationConfig) {
            this.toReturn = simulationConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
        }

        @Override
        public T buildAndGetConfig() {
            //build an object and check validation
            return toReturn;
        }
    }
}
