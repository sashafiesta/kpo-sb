package com.sashafiesta.zoo.animal;

public class Tiger extends Predator {
    public Tiger(String name, int food, int inventoryNumber) {
        super(name, food, inventoryNumber);
    }

    @Override
    public String toString() {
        return "Тигр по имени " + this.getName() + ", с номером " + this.getNumber();
    }
}
