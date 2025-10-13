package com.sashafiesta.zoo;

import com.sashafiesta.zoo.animal.Animal;
import com.sashafiesta.zoo.animal.Rabbit;
import com.sashafiesta.zoo.animal.Tiger;
import com.sashafiesta.zoo.animal.Wolf;
import com.sashafiesta.zoo.item.Computer;
import com.sashafiesta.zoo.item.InventoryItem;
import com.sashafiesta.zoo.item.Table;
import com.sashafiesta.zoo.service.impl.ZooImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ZooTests {
    private ZooImpl zoo;

    @BeforeEach
    public void setup() {
        zoo = new ZooImpl();
    }

    @Test
    public void addItemsTest() {
        InventoryItem item1 = new Computer("Computer", zoo.getNextInventoryNumber());
        assertTrue(zoo.addItem(item1));

        InventoryItem item2 = new Table("Table", zoo.getNextInventoryNumber());
        assertTrue(zoo.addItem(item2));

        assertTrue(zoo.getNextInventoryNumber() == 3);
        assertTrue(item1.getNumber() < item2.getNumber());
        assertTrue(zoo.getItems().stream().anyMatch(item -> item.getNumber() == item1.getNumber()));
        assertTrue(zoo.getItems().stream().anyMatch(item -> item.getNumber() == item2.getNumber()));
        assertTrue(zoo.getInventory().stream().anyMatch(item -> item.getNumber() == item1.getNumber()));
        assertTrue(zoo.getInventory().stream().anyMatch(item -> item.getNumber() == item2.getNumber()));
    }

    @Test
    public void addAnimalsTest() {
        Animal animal1 = new Rabbit("Rabbit", 1, zoo.getNextInventoryNumber(), 10);
        animal1.setHealthy(true);
        assertTrue(zoo.addAnimal(animal1));

        Animal animal2 = new Wolf("Wolf", 1, zoo.getNextInventoryNumber());
        animal2.setHealthy(true);
        assertTrue(zoo.addAnimal(animal2));


        assertTrue(zoo.getNextInventoryNumber() == 3);
        assertTrue(animal1.getNumber() < animal2.getNumber());
        assertTrue(zoo.getAnimals().stream().anyMatch(animal -> animal.getNumber() == animal1.getNumber()));
        assertTrue(zoo.getAnimals().stream().anyMatch(animal -> animal.getNumber() == animal2.getNumber()));
        assertTrue(zoo.getInventory().stream().anyMatch(animal -> animal.getNumber() == animal1.getNumber()));
        assertTrue(zoo.getInventory().stream().anyMatch(animal -> animal.getNumber() == animal2.getNumber()));
    }

    @Test
    public void vetTest() {
        Animal animal1 = new Rabbit("Rabbit", 1, 1, 10);
        animal1.setHealthy(true);
        assertTrue(zoo.addAnimal(animal1));

        Animal animal2 = new Wolf("Wolf", 1, 2);
        animal2.setHealthy(false);
        assertFalse(zoo.addAnimal(animal2));
    }

    @Test
    public void removeTest() {
        Animal animal1 = new Tiger("Tiger", 1, 1);
        animal1.setHealthy(true);
        assertTrue(zoo.addAnimal(animal1));

        InventoryItem item1 = new Computer("Computer", 2);
        assertTrue(zoo.addItem(item1));

        zoo.removeItem(animal1.getNumber());
        assertTrue(zoo.getAnimals().size() == 1);
        assertTrue(zoo.getItems().size() == 1);

        zoo.removeItem(item1.getNumber());
        assertTrue(zoo.getAnimals().size() == 1);
        assertTrue(zoo.getItems().size() == 0);

        zoo.removeAnimal(item1.getNumber());
        assertTrue(zoo.getAnimals().size() == 1);
        assertTrue(zoo.getItems().size() == 0);

        zoo.removeAnimal(animal1.getNumber());
        assertTrue(zoo.getAnimals().size() == 0);
        assertTrue(zoo.getItems().size() == 0);
    }
}
