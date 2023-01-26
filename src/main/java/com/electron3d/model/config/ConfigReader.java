package com.electron3d.model.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigReader {

    public static List<String> readLinesFromCsv(String configFileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(ConfigReader.class.getResource("/" + configFileName)).getPath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    //todo
    public static IslandSimulationConfig readConfigFromJSON(String configFileName) {
        IslandSimulationConfig config = null;
        return config;
    }
}
