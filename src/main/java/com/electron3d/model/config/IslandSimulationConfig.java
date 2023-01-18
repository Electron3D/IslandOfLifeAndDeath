package com.electron3d.model.config;

import java.util.List;

public class IslandSimulationConfig extends Config {
    //todo add started parameters for simulation such as island size, speed of simulation, etc...
    private static IslandSimulationConfig INSTANCE;
    private int speedOfSimulation;
    private int maxTimer;
    private IslandLength islandLength;//todo

    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimulationConfigBuilder<>(new IslandSimulationConfig()).buildAndGetConfig();
        }
        return INSTANCE;
    }

    private IslandSimulationConfig() {
    }

    public int getSpeedOfSimulation() {
        return speedOfSimulation;
    }

    public int getMaxTimer() {
        return maxTimer;
    }

    public IslandLength getIslandLength() {
        return islandLength;
    }

    //todo
    public static class IslandLength {
        private int parallelLength;

        private int meridianLength;

        public IslandLength(int parallelLength, int meridianLength) {
            this.parallelLength = parallelLength;
            this.meridianLength = meridianLength;
        }

        public int getParallelLength() {
            return parallelLength;
        }

        public void setParallelLength(int parallelLength) {
            this.parallelLength = parallelLength;
        }

        public int getMeridianLength() {
            return meridianLength;
        }

        public void setMeridianLength(int meridianLength) {
            this.meridianLength = meridianLength;
        }
    }

    public static class SimulationConfigBuilder<T extends IslandSimulationConfig> extends ConfigBuilder<T> {
        //todo add more specific methods to builder
        private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.csv";

        public SimulationConfigBuilder(T simulationConfig) {
            this.toReturn = simulationConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
        }

        @Override
        protected List<String> readLinesFromCsv() {
            return null;
        }
        private IslandLength setupIslandLength() {
            return new IslandLength(10,10);
        }
        @Override
        public T buildAndGetConfig() {
            //build an object and check validation
            return toReturn;
        }
    }
}
