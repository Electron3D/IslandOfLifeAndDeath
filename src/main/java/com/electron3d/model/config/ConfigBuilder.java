package com.electron3d.model.config;

import java.util.List;

public abstract class ConfigBuilder<Config> {
    protected Config toReturn;

    protected ConfigBuilder() {
    }
    protected abstract void initFieldsFromSourceFile();
    protected abstract List<String> readLinesFromCsv();
    public abstract Config buildAndGetConfig();
}
