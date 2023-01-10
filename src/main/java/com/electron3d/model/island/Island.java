package com.electron3d.model.island;

import com.electron3d.model.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Island {
    private final int parallelLength;
    private final int meridianLength;
    private final Field[][] fields;
    private List<Animal> animalPull;
    private final Random random = new Random();

    public Island(int parallelLength, int meridianLength) {
        this.parallelLength = parallelLength;
        this.meridianLength = meridianLength;
        this.fields = new Field[meridianLength][parallelLength];
    }

    public void init() {
        initAnimalPull();
        initFields();
    }

    private void initAnimalPull() {
        //init allAnimals;
        animalPull = new ArrayList<>();
    }

    private void initFields() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                fields[y][x] = new Field(x, y);
                Field field = fields[y][x];
                initPlants(field);
                initAnimals(field);
            }
        }
    }

    private void initPlants(Field field) {
        field.setPlantsOnThisField(random.nextInt(200));
    }
    private void initAnimals(Field field) {
        for (Animal animal: animalPull) {
            field.amountOfAnimalsOnTheField.put(animal.getClass().getSimpleName(), random.nextInt(animal.getBoundOnTheSameField()));
        }
    }

    @Override
    public String toString() {
        StringBuilder fieldsToString = new StringBuilder();
        for (Field[] fieldsOnTheSameMeridian : fields) {
            for (Field field : fieldsOnTheSameMeridian) {
                fieldsToString.append(field.toString()).append(" ");
                if (field.getX() < 10) {
                    fieldsToString.append(" ");
                }
                if (field.getY() < 10) {
                    fieldsToString.append(" ");
                }
                if (field.getPlantsOnThisField() < 100) {
                    fieldsToString.append(" ");
                }
            }
            fieldsToString.append("\n");
        }
        return "Island\n{" +
                "parallelLength=" + parallelLength +
                ", meridianLength=" + meridianLength +
                ", fields=\n" + fieldsToString +
                "}";
    }

}



