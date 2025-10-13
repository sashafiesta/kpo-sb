package com.sashafiesta.zoo.item;

public class Computer extends InventoryItem {
    public Computer(String name, int number) {
        super(name, number);
    }

    @Override
    public String toString() {
        return "Стол с названием " + this.getName() + ", с номером " + this.getNumber();
    }
}
