package com.sashafiesta.zoo.cli;

import com.sashafiesta.zoo.animal.*;
import com.sashafiesta.zoo.generic.IInventory;
import com.sashafiesta.zoo.item.Computer;
import com.sashafiesta.zoo.item.InventoryItem;
import com.sashafiesta.zoo.item.Table;
import com.sashafiesta.zoo.service.IZoo;

import java.util.Scanner;

public class CLIRunner {
    private static Integer getInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception exception) {
            return null;
        }
    }

    private static int menuAsk(Scanner scanner, String form, String[] elements) {
        System.out.println(form);
        for(int i = 0; i < elements.length; i++) {
            System.out.printf("%d) %s", i + 1, elements[i]);
            System.out.println();
        }
        Integer key;
        do {
            System.out.printf("Введите число от 1 до %d: ", elements.length);
            key = getInt(scanner);
        } while (key == null || key < 0 || key > elements.length);
        return key;
    }

    private static Animal getAnimal(IZoo zoo, Scanner scanner) {
        int type = menuAsk(scanner, "Выберите тип животного", new String[] {
                "Обезъяна",
                "Кролик",
                "Тигр",
                "Волк"
        });

        String name;
        do {
            System.out.print("Введите имя животного: ");
            name = scanner.nextLine();
        } while (name == null || name.isEmpty());

        boolean healthy = menuAsk(scanner, "Выберите состояние здоровья животного", new String[] {
                "Здорово",
                "Не здорово"
        }) == 1;

        Integer food;
        do {
            System.out.print("Введите потребление еды в кг/д: ");
            food = getInt(scanner);
        } while (food == null || food < 0);

        Integer level = -1;
        if (type == 1 || type == 2) {
            do {
                System.out.print("Введите уровень доброты (от 0 до 10): ");
                level = getInt(scanner);
            } while (level == null || level < 0 || level > 10);
        }

        boolean confirm = menuAsk(scanner, "Подтвердить ввод", new String[] {
                "Да",
                "Отменить"
        }) == 1;

        if (!confirm) {
            return null;
        }
        Animal animal;
        switch (type) {
            case 1:
                animal = new Monkey(name, food, zoo.getNextInventoryNumber(), level);
                break;
            case 2:
                animal = new Rabbit(name, food, zoo.getNextInventoryNumber(), level);
                break;
            case 3:
                animal = new Tiger(name, food, zoo.getNextInventoryNumber());
                break;
            case 4:
                animal = new Wolf(name, food, zoo.getNextInventoryNumber());
                break;
            default:
                return null;
        }
        animal.setHealthy(healthy);
        return animal;
    }

    private static InventoryItem getItem(IZoo zoo, Scanner scanner) {
        int type = menuAsk(scanner, "Выберите тип предмета", new String[] {
                "Компьютер",
                "Стол"
        });

        String name;
        do {
            System.out.print("Введите название предмета: ");
            name = scanner.nextLine();
        } while (name == null || name.isEmpty());

        boolean confirm = menuAsk(scanner, "Подтвердить ввод", new String[] {
                "Да",
                "Отменить"
        }) == 1;

        if (!confirm) {
            return null;
        }

        return switch (type) {
            case 1 -> new Computer(name, zoo.getNextInventoryNumber());
            case 2 -> new Table(name, zoo.getNextInventoryNumber());
            default -> null;
        };
    }

    public static void run(IZoo zoo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в программу \"Московский зоопарк\"!");
        boolean running = true;
        while (running) {
            int option = menuAsk(scanner, "Введите номер операции", new String[]
                    {
                            "Добавить животное на баланс зоопарка",
                            "Добавить предмет на баланс зоопарка",
                            "Убрать животное с баланса зоопарка",
                            "Убрать предмет с баланса зоопарка",
                            "Вывести на экран список животных",
                            "Вывести на экран список предметов",
                            "Вывести на экран список всех инвентаризируемых объектов",
                            "Вывести на экран животных, подходящих для контакта с посетителями",
                            "Вычислить суммарное потребление еды",
                            "Завершить работу программы"
                    });
            Integer input;
            switch (option) {
                case 1:
                    Animal newAnimal = getAnimal(zoo, scanner);
                    if (newAnimal == null) {
                        System.out.println("Операция отменена");
                        break;
                    }
                    if (newAnimal.isHealthy()) {
                        System.out.println("Животное успешно добавлено");
                        //Оно ломается из-за win1251 иногда
                        System.out.printf("Инвентарный номер: %d", newAnimal.getNumber());
                        System.out.println();
                        zoo.addAnimal(newAnimal);
                    } else {
                        System.out.println("Невозможно исполнить: животное отклонено ветеринарной клиникой");
                    }
                    break;
                case 2:
                    InventoryItem newItem = getItem(zoo, scanner);
                    if (newItem == null) {
                        System.out.println("Операция отменена");
                        break;
                    }
                    System.out.println("Предмет успешно добавлен");
                    System.out.printf("Инвентарный номер: %d", newItem.getNumber());
                    System.out.println();
                    zoo.addItem(newItem);
                    break;
                case 3:
                    System.out.print("Введите инвентарный номер: ");
                    input = getInt(scanner);
                    if (input != null && zoo.removeAnimal(input)) {
                        System.out.println("Операция успешно исполнена");
                    } else {
                        System.out.println("Невозможно исполнить: некорректный вводили животное не найдено");
                    }
                    break;
                case 4:
                    System.out.print("Введите инвентарный номер: ");
                    input = getInt(scanner);
                    if (input != null && zoo.removeItem(input)) {
                        System.out.println("Операция успешно исполнена");
                    } else {
                        System.out.println("Невозможно исполнить: некорректный ввод или предмет не найден");
                    }
                    break;
                case 5:
                    System.out.printf("Список животных на балансе зоопарка (всего %d): ", zoo.getAnimals().size());
                    System.out.println();
                    for(Animal animal : zoo.getAnimals()) {
                        System.out.println(animal);
                    }
                    break;
                case 6:
                    System.out.printf("Список предметов на балансе зоопарка (всего %d): ",  zoo.getItems().size());
                    System.out.println();
                    for(InventoryItem item : zoo.getItems()) {
                        System.out.println(item);
                    }
                    break;
                case 7:
                    System.out.printf("Список инвентаризируемых объектов (всего %d): ", zoo.getInventory().size());
                    System.out.println();
                    for(IInventory element : zoo.getInventory()) {
                        System.out.println(element);
                    }
                    break;
                case 8:
                    System.out.printf("Список животных, подходящих для контакта с посетителями (всего %d): ", zoo.getContactableAnimals().size());
                    System.out.println();
                    for(Animal animal : zoo.getContactableAnimals()) {
                        System.out.println(animal);
                    }
                    break;
                case 9:
                    System.out.printf("Суммарное потребление еды: %d кг/день", zoo.getFoodConsumption());
                    System.out.println();
                    break;
                case 10:
                    running = false;
                    break;

            }
            System.out.println();
        }
        System.out.println("До свидания!");
    }
}
