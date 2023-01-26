package com.electron3d.model.config;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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

    public static IslandSimulationConfig readConfigFromJSON(String configFileName) {
        URL resource = ConfigReader.class.getResource("/" + configFileName);
        File file;
        IslandSimulationConfig config;
        try {
            file = Paths.get(Objects.requireNonNull(resource).toURI()).toFile();
            JsonMapper mapper = new JsonMapper();
            config = mapper.readValue(file, IslandSimulationConfig.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
}
