package com.electron3d.model.config;

import java.util.List;

public class IslandSimulationConfig extends Config {
    private static IslandSimulationConfig INSTANCE;
    private int speedOfSimulation;
    private int maxTimer;
    private final IslandLength islandLength = new IslandLength();
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

    public void setSpeedOfSimulation(int speedOfSimulation) {
        this.speedOfSimulation = speedOfSimulation;
    }

    public int getMaxTimer() {
        return maxTimer;
    }

    public void setMaxTimer(int maxTimer) {
        this.maxTimer = maxTimer;
    }

    public IslandLength getIslandLength() {
        return islandLength;
    }

    public static class IslandLength {
        int parallelLength;
        int meridianLength;
        public IslandLength() {
        }
        public int getParallelLength() {
            return parallelLength;
        }
        public int getMeridianLength() {
            return meridianLength;
        }
    }

    private static class SimulationConfigBuilder<T extends IslandSimulationConfig> extends ConfigBuilder<T> {
        private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.csv";

        public SimulationConfigBuilder(T simulationConfig) {
            this.toReturn = simulationConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv();
            setupSimulationParameters(lines);
            setupIslandLength(lines);
        }

        @Override
        protected List<String> readLinesFromCsv() {
            //todo
            return null;
        }

        private void setupSimulationParameters(List<String> lines) {
            //todo
        }

        private void setupIslandLength(List<String> lines) {
            IslandLength length = toReturn.getIslandLength();
            //todo
            length.meridianLength = 10;
            length.parallelLength = 10;
        }
        @Override
        public T buildAndGetConfig() {
            //todo build an object and check validation

            return toReturn;
        }
    }
}
