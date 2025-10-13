package com.sashafiesta.zoo.animal;
import com.sashafiesta.zoo.generic.IAlive;
import com.sashafiesta.zoo.generic.IInventory;

public abstract class Animal implements IAlive, IInventory {
    private String name;
    private int food;
    private int inventoryNumber;
    private boolean healthy;

    public Animal(String name, int food, int inventoryNumber) {
        this.name = name;
        this.food = food;
        this.inventoryNumber = inventoryNumber;
        this.healthy = false;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getFood() {
        return food;
    }

    @Override
    public int getNumber() {
        return inventoryNumber;
    }

    @Override
    public void setNumber(int number) {
        inventoryNumber = number;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
