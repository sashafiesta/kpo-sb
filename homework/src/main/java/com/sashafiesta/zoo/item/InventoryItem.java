package com.sashafiesta.zoo.item;

import com.sashafiesta.zoo.generic.IInventory;

public abstract class InventoryItem implements IInventory {
    private String name;
    private int inventoryNumber;

    public InventoryItem(String name, int inventoryNumber) {
        this.name = name;
        this.inventoryNumber = inventoryNumber;
    }

    public String getName() {
        return name;
    }

    @Override public int getNumber() {
        return inventoryNumber;
    }

    @Override public void setNumber(int number) {
        inventoryNumber = number;
    }
}
