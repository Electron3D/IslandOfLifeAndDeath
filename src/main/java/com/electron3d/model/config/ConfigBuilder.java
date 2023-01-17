package com.electron3d.model.config;

public class ConfigBuilder {

    private Config config;

    public void built() {
        config = IslandConfig.getInstance();
    }
}
