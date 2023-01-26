package com.electron3d.model.config;

public class IslandSimulationConfig {
    private static IslandSimulationConfig INSTANCE;
    private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.json";
    private int timeMultiplier;
    private int maxTimeOfSimulationInSeconds;
    private int xDimension;
    private int yDimension;

    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = ConfigReader.readConfigFromJSON(SIMULATION_SPEC_FILE_NAME);
        }
        return INSTANCE;
    }

    private IslandSimulationConfig() {
    }

    public int getTimeMultiplier() {
        return timeMultiplier;
    }

    public int getMaxTimeOfSimulationInSeconds() {
        return maxTimeOfSimulationInSeconds;
    }

    public void setTimeMultiplier(int timeMultiplier) {
        this.timeMultiplier = timeMultiplier;
    }

    public void setMaxTimeOfSimulationInSeconds(int maxTimeOfSimulationInSeconds) {
        this.maxTimeOfSimulationInSeconds = maxTimeOfSimulationInSeconds;
    }

    public int getxDimension() {
        return xDimension;
    }

    public int getyDimension() {
        return yDimension;
    }

    public void setxDimension(int xDimension) {
        this.xDimension = xDimension;
    }

    public void setyDimension(int yDimension) {
        this.yDimension = yDimension;
    }
}
