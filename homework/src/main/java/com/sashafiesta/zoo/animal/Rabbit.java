package com.sashafiesta.zoo.animal;

public class Rabbit extends Herbo {
    public Rabbit(String name, int food, int inventoryNumber, int kindness) {
        super(name, food, inventoryNumber, kindness);
    }
    @Override
    public String toString() {
        return "Кролик по имени " + this.getName() + ", с номером " + this.getNumber();
    }
}

