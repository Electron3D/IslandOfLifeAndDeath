package com.electron3d.model.config;

public class IslandSimulationConfig {
    private static IslandSimulationConfig INSTANCE;
    private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.json";
    private final int timeMultiplier;
    private final int maxTimeOfSimulationInSeconds;
    private final IslandDimensions islandDimensions;

    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = ConfigReader.readConfigFromJSON(SIMULATION_SPEC_FILE_NAME);
        }
        return INSTANCE;
    }

    private IslandSimulationConfig(int timeMultiplier, int maxTimeOfSimulationInSeconds, IslandDimensions islandDimensions) {
        this.timeMultiplier = timeMultiplier;
        this.maxTimeOfSimulationInSeconds = maxTimeOfSimulationInSeconds;
        this.islandDimensions = islandDimensions;
    }

    public int getTimeMultiplier() {
        return timeMultiplier;
    }

    public int getMaxTimeOfSimulationInSeconds() {
        return maxTimeOfSimulationInSeconds;
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
}
