package com.electron3d.model.island;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Field {

    //todo private modifiers and correct access for collections
    public final Map<String, Integer> amountOfAnimalsOnTheField = new HashMap<>();
    public final List<Animal> animalsOnTheField = new ArrayList<>();
    public final List<Plant> plantsOnTheField = new ArrayList<>();
    public final List<Field> possibleWays = new ArrayList<>();
    private final int x;
    private final int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAmountOfPlantsOnTheField() {
        return plantsOnTheField.size();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|plants:" + getAmountOfPlantsOnTheField() + "|animals:" + amountOfAnimalsOnTheField.values().stream().reduce(Integer::sum).orElseThrow() + "}"; //todo
    }
}
