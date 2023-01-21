package com.electron3d.model.config;

import java.util.List;

/**
 * Every Config class inheritor should have its own builder as inner class in it extended from ConfigBuilder class.
 */
public class IslandSimulationConfig extends Config {
    private static IslandSimulationConfig INSTANCE;
    private int timeMultiplier;
    private int maxTimeOfSimulationInSeconds;
    private final IslandDimensions islandDimensions = new IslandDimensions();
    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimulationConfigBuilder<>(new IslandSimulationConfig()).buildAndGetConfig();
        }
        return INSTANCE;
    }

    private IslandSimulationConfig() {
    }

    public int getTimeMultiplier() {
        return timeMultiplier;
    }

    public void setTimeMultiplierOfSimulation(int speedOfSimulation) {
        this.timeMultiplier = speedOfSimulation;
    }

    public int getMaxTimeOfSimulationInSeconds() {
        return maxTimeOfSimulationInSeconds;
    }

    public void maxTimeOfSimulationInSeconds(int maxTimer) {
        this.maxTimeOfSimulationInSeconds = maxTimer;
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

    /**
     * There is an example of Config "toReturn" that Builder should configure,
     * initialise fields, check validation and then return.
     **/
    private static class SimulationConfigBuilder<T extends IslandSimulationConfig> extends ConfigBuilder<T> {
        private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.csv";

        public SimulationConfigBuilder(T simulationConfig) {
            this.toReturn = simulationConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv(SIMULATION_SPEC_FILE_NAME);
            setupSimulationParameters(lines);
            setupIslandDimensions(lines);
        }

        private void setupSimulationParameters(List<String> lines) {
            //todo
            toReturn.maxTimeOfSimulationInSeconds(30);
            toReturn.setTimeMultiplierOfSimulation(4);
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
            initFieldsFromSourceFile();
            return toReturn;
        }
    }
}
