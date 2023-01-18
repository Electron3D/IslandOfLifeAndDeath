package com.electron3d.model.config;

import java.util.List;

public class IslandSimulationConfig extends Config {
    private static IslandSimulationConfig INSTANCE;
    private int speedOfSimulation;
    private int maxTimer;
    private final IslandDimensions islandDimensions = new IslandDimensions();
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

    public IslandDimensions getIslandDimensions() {
        return islandDimensions;
    }

    public static class IslandDimensions {
        int xDimension;
        int yDimension;
        public IslandDimensions() {
        }
        public int getXDimension() {
            return xDimension;
        }
        public int getYDimension() {
            return yDimension;
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
            setupIslandDimensions(lines);
        }

        @Override
        protected List<String> readLinesFromCsv() {
            //todo
            return null;
        }

        private void setupSimulationParameters(List<String> lines) {
            //todo
        }

        private void setupIslandDimensions(List<String> lines) {
            IslandDimensions dimensions = toReturn.getIslandDimensions();
            //todo
            dimensions.yDimension = 10;
            dimensions.xDimension = 10;
        }
        @Override
        public T buildAndGetConfig() {
            //todo build an object and check validation

            return toReturn;
        }
    }
}
