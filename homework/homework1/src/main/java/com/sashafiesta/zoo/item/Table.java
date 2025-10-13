package com.sashafiesta.zoo.item;

public class Table extends InventoryItem {
    public Table(String name, int number) {
        super(name, number);
    }
    @Override
    public String toString() {
        return "Компьютер с названием " + this.getName() + ", с номером " + this.getNumber();
    }
}
