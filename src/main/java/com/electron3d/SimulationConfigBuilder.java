package com.electron3d;

import com.electron3d.model.SimulationConfig;
import com.electron3d.model.IslandSimulationConfig;

public class SimulationConfigBuilder {

    private SimulationConfig simulationConfig;

    void built() {
        simulationConfig = IslandSimulationConfig.getInstance();
    }
}
