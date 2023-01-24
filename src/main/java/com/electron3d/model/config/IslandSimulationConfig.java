package com.electron3d.model.config;

public class IslandSimulationConfig {
    private static IslandSimulationConfig INSTANCE;
    private int timeMultiplier;
    private int maxTimeOfSimulationInSeconds;
    private final IslandDimensions islandDimensions = new IslandDimensions();
    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IslandSimulationConfigBuilder(new IslandSimulationConfig()).buildAndGetConfig();
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

    public void maxNumberOfSimulationDays(int maxTimer) {
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
}
