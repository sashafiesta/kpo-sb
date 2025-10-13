package com.sashafiesta.zoo.service.impl;

import com.sashafiesta.zoo.animal.Animal;
import com.sashafiesta.zoo.animal.Herbo;
import com.sashafiesta.zoo.generic.IInventory;
import com.sashafiesta.zoo.item.InventoryItem;
import com.sashafiesta.zoo.service.IVeterinaryClinic;
import com.sashafiesta.zoo.service.IZoo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ZooImpl implements IZoo {
    private IVeterinaryClinic veterinaryClinic = new VeterinaryClinicImpl();
    private List<Animal> animals = new ArrayList<>();
    private List<InventoryItem> items = new ArrayList<>();
    private int lastInventoryNumber = 0;

    @Override
    public boolean addAnimal(Animal animal) {
        if (veterinaryClinic.isHealthy(animal)) {
            animals.add(animal);
            if (animal.getNumber() > lastInventoryNumber) {
                lastInventoryNumber = animal.getNumber();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addItem(InventoryItem item) {
        items.add(item);
        if (item.getNumber() > lastInventoryNumber) {
            lastInventoryNumber = item.getNumber();
        }
        return true;
    }

    @Override
    public boolean removeAnimal(int number) {
        return animals.removeIf(animal -> animal.getNumber() == number);
    }

    @Override
    public boolean removeItem(int number) {
        return items.removeIf(item -> item.getNumber() == number);
    }

    @Override
    public int getNextInventoryNumber() {
        return lastInventoryNumber + 1;
    }


    @Override
    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public List<Animal> getContactableAnimals() {
        return animals.stream()
                .filter(animal -> animal instanceof Herbo)
                .map(animal -> (Herbo)animal)
                .filter(Herbo::canBeContacted)
                .map(animal -> (Animal)animal)
                .toList();
    }

    @Override
    public List<InventoryItem> getItems() {
        return items;
    }

    @Override
    public int getFoodConsumption() {
        return animals.stream()
                .mapToInt(Animal::getFood)
                .sum();
    }

    @Override
    public List<IInventory> getInventory() {
        return Stream.concat(
                items.stream()
                        .map(item -> (IInventory)item),
                animals.stream()
                        .map(animal -> (IInventory)animal)
        ).toList();
    }
}
