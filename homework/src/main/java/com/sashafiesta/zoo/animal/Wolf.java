package com.sashafiesta.zoo.animal;

public class Wolf extends Predator {
    public Wolf(String name, int food, int inventoryNumber) {
        super(name, food, inventoryNumber);
    }

    @Override
    public String toString() {
        return "Волк по имени " + this.getName() + ", с номером " + this.getNumber();
    }
}
