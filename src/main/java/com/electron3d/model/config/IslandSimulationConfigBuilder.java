package com.electron3d.model.config;

import java.util.List;

public class IslandSimulationConfigBuilder {
    private static final String SIMULATION_SPEC_FILE_NAME = "simulationCfg.csv";

    private final IslandSimulationConfig toReturn;

    public IslandSimulationConfigBuilder (IslandSimulationConfig simulationConfig) {
        this.toReturn = simulationConfig;
    }

    protected void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(SIMULATION_SPEC_FILE_NAME);
        setupSimulationParameters(lines);
        setupIslandDimensions(lines);
    }

    private void setupSimulationParameters(List<String> lines) {
        //todo
        toReturn.maxNumberOfSimulationDays(150);
        toReturn.setTimeMultiplierOfSimulation(8);
    }

    private void setupIslandDimensions(List<String> lines) {
        IslandSimulationConfig.IslandDimensions dimensions = toReturn.getIslandDimensions();
        //todo
        dimensions.yDimension = 10;
        dimensions.xDimension = 10;
    }
    public IslandSimulationConfig buildAndGetConfig() {
        //todo build an object and check validation
        initFieldsFromSourceFile();
        return toReturn;
    }
}
