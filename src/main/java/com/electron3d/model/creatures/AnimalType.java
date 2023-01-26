package com.electron3d.model.creatures;

public enum AnimalType {
    WOLF("wolf", "\uD83D\uDC3A"),
    PYTHON("python", "\uD83D\uDC0D"),
    FOX("fox", "\uD83E\uDD8A"),
    BEAR("bear", "\uD83D\uDC3B"),
    EAGLE("eagle", "\uD83E\uDD85"),
    HORSE("horse", "\uD83D\uDC0E"),
    DEER("deer", "\uD83E\uDD8C"),
    RABBIT("rabbit", "\uD83D\uDC07"),
    MOUSE("mouse", "\uD83D\uDC01"),
    GOAT("goat", "\uD83D\uDC10"),
    SHEEP("sheep", "\uD83D\uDC11"),
    BOAR("boar", "\uD83D\uDC17"),
    BUFFALO("buffalo", "\uD83D\uDC03"),
    DUCK("duck", "\uD83E\uDD86"),
    CATERPILLAR("caterpillar", "\uD83D\uDC1B");

    private final String type;
    private final String icon;

    AnimalType(String type, String icon) {
        this.type = type;
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }
}
