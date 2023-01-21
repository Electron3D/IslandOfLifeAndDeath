package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.*;

public class Island {
    private final int xDimension;
    private final int yDimension;
    private final Field[][] fields;
    private final List<String> animalTypes;
    private final List<Plant> plantsPull  = new ArrayList<>();
    private final List<Animal> animalsPull = new ArrayList<>();
    private final List<Animal> graveYard = new ArrayList<>();

    public Island(int xDimension, int yDimension, List<String> animalTypes) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.fields = new Field[yDimension][xDimension];
        this.animalTypes = animalTypes;
    }

    public void initFields() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                fields[y][x] = new Field(x, y);
                Field field = fields[y][x];
                field.plantsOnTheField.addAll(initPlants(field));
                field.animalsOnTheField.addAll(initAnimals(field));
            }
        }
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                field.possibleWays.addAll(initPossibleWays(field));
            }
        }
    }

    private List<Plant> initPlants(Field field) {
        Random startingPlantsCountChooser = new Random();
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        int amountOfPlantsOnTheField = startingPlantsCountChooser.nextInt(properties.getBoundOnTheSameField());
        List<Plant> plantsOnTheField = new ArrayList<>();
        for (int i = 0; i < amountOfPlantsOnTheField; i++) {
            plantsOnTheField.add(new Plant(properties, field));
        }
        plantsPull.addAll(plantsOnTheField);
        return plantsOnTheField;
    }

    private List<Animal> initAnimals(Field field) {
        Random startingAnimalsCountChooser = new Random();
        List<AnimalProperties> animalsProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        for (String animalType : animalTypes) {
            AnimalProperties animalProperties = animalsProperties.stream().filter(x -> animalType.equals(x.getType())).findFirst().orElseThrow();
            field.amountOfAnimalsOnTheField.put(animalType, startingAnimalsCountChooser.nextInt(animalProperties.getBoundOnTheSameField()));
        }
        AnimalFactory factory = new AnimalFactory();
        List<Animal> animalsOnTheField = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : field.amountOfAnimalsOnTheField.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Animal animal = factory.createAnimal(entry.getKey(), field);
                animalsOnTheField.add(animal);
            }
        }
        animalsPull.addAll(animalsOnTheField);
        return animalsOnTheField;
    }

    private List<Field> initPossibleWays(Field field) {
        List<Field> possibleWays = new ArrayList<>();
        int i0 = field.getX();
        int j0 = field.getY();
        int height = fields.length;
        int width = fields[0].length;
        for (int i = i0 - 1; i <= i0 + 1; ++i) {
            for (int j = j0 - 1; j <= j0 + 1; ++j) {
                if (0 <= i && i < height && 0 <= j && j < width && (i != i0 || j != j0)) {
                    possibleWays.add(fields[i][j]);
                }
            }
        }
        return possibleWays;
    }
    public void growPlants() {
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        for (int i = 0; i < plantsPull.size(); i++) {
            Plant plant = plantsPull.get(i);
            List<Plant> newGrownPlants = new ArrayList<>();
            int numberOfNewGrownPlants = plant.grow();
            Field location = plant.getLocation();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, location));
            }
            addPlants(newGrownPlants, location);
        }
    }

    public void doAnimalStuff() {
        List<Animal> diedAnimalsToday = new ArrayList<>();
        for (Animal animal : animalsPull) {
            boolean diedThisDay = animal.liveADay();
            if (diedThisDay) {
                diedAnimalsToday.add(animal);
            }
        }
        buryAnimals(diedAnimalsToday);
    }

    private void buryAnimals(List<Animal> diedAnimalsToday) {
        for (Animal deadAnimal : diedAnimalsToday) {
            deleteAnimal(deadAnimal, deadAnimal.getLocation());
        }
        graveYard.addAll(diedAnimalsToday);
    }

    public int getAnimalsPullSize() {
        return animalsPull.size();
    }

    public int getPlantsPullSize() {
        return plantsPull.size();
    }

    public int getGraveYardSize() {
        return graveYard.size();
    }

    public void addPlants(List<Plant> newGrownPlantsToAdd, Field location) {
        synchronized (location.plantsOnTheField) {
            location.plantsOnTheField.addAll(newGrownPlantsToAdd);
        }
        synchronized (plantsPull) {
            plantsPull.addAll(newGrownPlantsToAdd);
        }
    }

    public synchronized boolean deletePlant(Plant plantToDelete) {
        return plantsPull.remove(plantToDelete);
    }

    public synchronized void addAnimal(Animal animalToAdd) {
        animalsPull.add(animalToAdd);
    }

    public void deleteAnimal(Animal animalToDelete, Field location) {
        synchronized (location.animalsOnTheField) {
            location.animalsOnTheField.remove(animalToDelete);
        }
        synchronized (animalsPull) {
            animalsPull.remove(animalToDelete);
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
                if (field.getAmountOfPlantsOnTheField() < 10) {
                    fieldsToString.append(" ");
                }
                if (field.getAmountOfPlantsOnTheField() < 100) {
                    fieldsToString.append(" ");
                }
            }
            fieldsToString.append("\n");
        }
        return "Island\n{" +
                "parallelLength=" + xDimension +
                ", meridianLength=" + yDimension +
                ", fields=\n" + fieldsToString +
                "}";
    }
}



