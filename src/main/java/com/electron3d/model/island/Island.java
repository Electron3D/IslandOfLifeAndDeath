package com.electron3d.model.island;

import com.electron3d.model.Animal;
import com.electron3d.model.Plant;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Island {
    private final int parallelLength;
    private final int meridianLength;
    private final Field[][] fields;
    private final Set<String> animalsSpecies;
    private final List<Plant> plantsPull;
    private final List<Animal> animalsPull;
    private final Random random = new Random();

    public Island(int parallelLength, int meridianLength, Set<String> animalsSpecies) {
        this.parallelLength = parallelLength;
        this.meridianLength = meridianLength;
        this.fields = new Field[meridianLength][parallelLength];
        this.animalsSpecies = animalsSpecies;
        this.animalsPull = new ArrayList<>();
        this.plantsPull = new ArrayList<>();
    }

    public void initFields() {
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
        field.setPlantsOnThisField(random.nextInt(Plant.BOUND_ON_THE_SAME_FIELD));
        for (int i = 0; i < field.getPlantsOnThisField(); i++) {
            plantsPull.add(new Plant(field));
        }
    }

    /*
    * оч плохо, над переделать, но работает
    */
    private void initAnimals(Field field) {
        for (String animalName : animalsSpecies) {
            field.amountOfAnimalsOnTheField.put(animalName, random.nextInt(60)); //add bound
        }
        for (Map.Entry<String, Integer> entry : field.amountOfAnimalsOnTheField.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                String path = "com.electron3d.model.animals.herbivores.";
                String animalName = entry.getKey();
                try {
                    animalsPull.add((Animal) Class.forName(path + animalName).getConstructor(double.class, int.class, double.class, Field.class).newInstance(400.0, 4, 60, field));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void growPlants() {
        List<Plant> newGrownPlants = new ArrayList<>();
        for (Plant plant : plantsPull) {
            if (plant.grow()) {
                newGrownPlants.add(new Plant(plant.getLocation()));
            }
        }
        plantsPull.addAll(newGrownPlants);
    }

    public List<Plant> getPlantsPull() {
        return plantsPull;
    }

    public List<Animal> getAnimalsPull() {
        return animalsPull;
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



