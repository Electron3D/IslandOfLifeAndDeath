package com.electron3d.model.island;

public class Island {
    private int parallelLength;
    private int meridianLength;
    private final Field[][] fields;
    public Island(int parallelLength, int meridianLength) {
        this.parallelLength = parallelLength;
        this.meridianLength = meridianLength;
        this.fields = new Field[meridianLength][parallelLength];
        initFields();
    }
    private void initFields() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                fields[y][x] = new Field(x, y);
            }
        }
    }

    public int getParallelLength() {
        return parallelLength;
    }

    public void setParallelLength(int parallelLength) {
        this.parallelLength = parallelLength;
    }

    public int getMeridianLength() {
        return meridianLength;
    }

    public void setMeridianLength(int meridianLength) {
        this.meridianLength = meridianLength;
    }

    @Override
    public String toString() {
        StringBuilder fieldsToString = new StringBuilder();
        for (Field[] fieldsOnTheSameMeridian : fields) {
            for (Field field : fieldsOnTheSameMeridian) {
                fieldsToString.append(field.toString()).append(" ");
                if (field.x < 10) {
                    fieldsToString.append(" ");
                }
                if (field.y < 10) {
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



