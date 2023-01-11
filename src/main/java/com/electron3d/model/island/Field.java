package com.electron3d.model.island;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Field {
    private final int x;

    private final int y;

    private final List<Field> possibleWays;

    private int plantsOnThisField;

    public final Map<String, Integer> amountOfAnimalsOnTheField;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.possibleWays = new ArrayList<>();
        this.amountOfAnimalsOnTheField = new HashMap<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Field> getPossibleWays() {
        return possibleWays;
    }

    public int getPlantsOnThisField() {
        return plantsOnThisField;
    }

    public void setPlantsOnThisField(int plantsOnThisField) {
        this.plantsOnThisField = plantsOnThisField;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|plants:" + plantsOnThisField + "|animals:" + amountOfAnimalsOnTheField.values().stream().reduce(Integer::sum).orElseThrow() + "}"; //temporary
    }
}
