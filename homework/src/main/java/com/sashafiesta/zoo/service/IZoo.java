package com.sashafiesta.zoo.service;

import com.sashafiesta.zoo.animal.Animal;
import com.sashafiesta.zoo.generic.IInventory;
import com.sashafiesta.zoo.item.InventoryItem;

import java.util.List;

public interface IZoo {
    boolean addAnimal(Animal animal);
    boolean addItem(InventoryItem item);
    boolean removeAnimal(int number);
    boolean removeItem(int number);

    int getNextInventoryNumber();

    List<Animal> getAnimals();
    List<Animal> getContactableAnimals();
    List<InventoryItem> getItems();
    int getFoodConsumption();
    List<IInventory> getInventory();
}
