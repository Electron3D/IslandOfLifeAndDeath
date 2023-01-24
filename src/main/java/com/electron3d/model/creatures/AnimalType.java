package com.electron3d.model.creatures;

public enum AnimalType {
    WOLF("wolf"),
    PYTHON("python"),
    FOX("fox"),
    BEAR("bear"),
    EAGLE("eagle"),
    HORSE("horse"),
    DEER("deer"),
    RABBIT("rabbit"),
    MOUSE("mouse"),
    GOAT("goat"),
    SHEEP("sheep"),
    BOAR("boar"),
    BUFFALO("buffalo"),
    DUCK("duck"),
    CATERPILLAR("caterpillar");

    private final String type;

    AnimalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
