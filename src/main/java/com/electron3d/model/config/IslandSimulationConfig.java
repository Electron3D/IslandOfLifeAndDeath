package com.electron3d.model.config;

import java.util.List;

public class IslandSimulationConfig {
    private static IslandSimulationConfig INSTANCE;
    private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.json";
    private int timeMultiplier;
    private int maxTimeOfSimulationInSeconds;
    private final IslandDimensions islandDimensions = new IslandDimensions();

    public static IslandSimulationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IslandSimulationConfig();
            INSTANCE.initFieldsFromSourceFile();
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

    public IslandDimensions getIslandDimensions() {
        return islandDimensions;
    }

    protected void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(SIMULATION_SPEC_FILE_NAME);
        setupSimulationParameters(lines);
        setupIslandDimensions(lines);
    }

    private void setupSimulationParameters(List<String> lines) {
        //todo
        maxTimeOfSimulationInSeconds = 150;
        timeMultiplier = 8;
    }

    private void setupIslandDimensions(List<String> lines) {
        //todo
        islandDimensions.yDimension = 10;
        islandDimensions.xDimension = 10;
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
