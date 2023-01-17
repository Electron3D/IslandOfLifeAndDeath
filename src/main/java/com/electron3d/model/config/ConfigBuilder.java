package com.electron3d.model.config;

public abstract class ConfigBuilder<Config> {
    protected Config toReturn;

    protected ConfigBuilder() {
    }
    protected abstract void initFieldsFromSourceFile();
    public abstract Config buildAndGetConfig();
}
