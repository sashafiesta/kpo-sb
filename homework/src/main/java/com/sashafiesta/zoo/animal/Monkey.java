package com.sashafiesta.zoo.animal;

public class Monkey extends Herbo {
    public Monkey(String name, int food, int inventoryNumber, int kindness) {
        super(name, food, inventoryNumber, kindness);
    }

    @Override
    public String toString() {
        return "Обезьяна по имени " + this.getName() + ", с номером " + this.getNumber();
    }
}

