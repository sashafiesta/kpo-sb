package com.sashafiesta.zoo.animal;

public abstract class Herbo extends Animal {
    private int kindness;

    public Herbo(String name, int food, int inventoryNumber, int kindness) {
        super(name, food, inventoryNumber);
        this.kindness = kindness;
    }

    public boolean canBeContacted() {
        return kindness > 5;
    }
}
