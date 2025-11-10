package com.sashafiesta.finances;


import com.sashafiesta.finances.analytics.AnalyticsFacade;
import com.sashafiesta.finances.command.Command;
import com.sashafiesta.finances.command.decorator.TimerDecorator;
import com.sashafiesta.finances.command.impl.CreateOperationCommand;
import com.sashafiesta.finances.command.impl.GetAccountBalanceCommand;
import com.sashafiesta.finances.di.DIContainer;
import com.sashafiesta.finances.di.DIManager;
import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.exporter.DataExporter;
import com.sashafiesta.finances.exporter.visitor.DataVisitor;
import com.sashafiesta.finances.exporter.visitor.JsonExportVisitor;
import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;
import com.sashafiesta.finances.factory.DomainFactory;
import com.sashafiesta.finances.factory.impl.DomainFactoryImpl;
import com.sashafiesta.finances.importer.DataImporter;
import com.sashafiesta.finances.importer.impl.JsonImporter;
import com.sashafiesta.finances.repository.BankAccountRepository;
import com.sashafiesta.finances.repository.CategoryRepository;
import com.sashafiesta.finances.repository.OperationRepository;
import com.sashafiesta.finances.repository.Repository;
import com.sashafiesta.finances.repository.impl.SimpleBankAccountRepository;
import com.sashafiesta.finances.repository.impl.SimpleCategoryRepository;
import com.sashafiesta.finances.repository.impl.SimpleOperationRepository;
import com.sashafiesta.finances.repository.impl.SimpleRepository;
import com.sashafiesta.finances.service.BalanceUpdateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static DIContainer diContainer;

    private static BankAccountFacade accountFacade;
    private static CategoryFacade categoryFacade;
    private static OperationFacade operationFacade;
    private static AnalyticsFacade analyticsFacade;
    private static DataExporter dataExporter;
    private static BalanceUpdateService updateService;

    private static Integer getInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception exception) {
            return null;
        }
    }

    private static Long getLong(Scanner scanner) {
        try {
            return Long.parseLong(scanner.nextLine());
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

    private static void createSampleData() {

        try {
            BankAccount firstAccount = accountFacade.createBankAccount("Счет Васи Пупкина", BigDecimal.ZERO);
            BankAccount secondAccount = accountFacade.createBankAccount("Счет Образца Образцова", new BigDecimal("150"));
            Category salaryCategory = categoryFacade.createCategory("Зарплата", CategoryType.INCOME);
            Category bonusCategory = categoryFacade.createCategory("Стипендия", CategoryType.INCOME);

            Category shopCategory = categoryFacade.createCategory("Магазины", CategoryType.EXPENSE);
            Category leisureCategory = categoryFacade.createCategory("Развлечения", CategoryType.EXPENSE);

            operationFacade.createOperation(CategoryType.INCOME, firstAccount.getId(), new BigDecimal("30000"), "Зарплата", salaryCategory.getId());
            operationFacade.createOperation(CategoryType.INCOME, secondAccount.getId(), new BigDecimal("2100"), "Стипендия", bonusCategory.getId());
            operationFacade.createOperation(CategoryType.EXPENSE, secondAccount.getId(), new BigDecimal("1000"), "Магазин обуви", shopCategory.getId());
            operationFacade.createOperation(CategoryType.EXPENSE, firstAccount.getId(), new BigDecimal("500"), "Кинотеатр", leisureCategory.getId());
        }
        catch (IllegalArgumentException exception) {
            System.out.println("how");
        }
    }

    public static void main(String[] args) {
        init();
        createSampleData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                int choice = menuAsk(scanner, "Введите номер опции", new String[] {
                        "Базовые операции",
                        "Команды",
                        "Аналитика",
                        "Экспорт",
                        "импорт",
                        "Обновить балансы на счетах",
                        "Выход"
                });
                switch (choice) {
                    case 1 -> basicOps(scanner);
                    case 2 -> commandOps(scanner);
                    case 3 -> analyticsOps(scanner);
                    case 4 -> exportOps(scanner);
                    case 5 -> importOps(scanner);
                    case 6 -> {
                        updateService.UpdateBalances();
                        System.out.println("Готово!");
                    }
                    case 7 -> running = false;
                    default -> System.out.println("Ошибка");
                }
            }
            catch (Exception exception) {
                System.out.println("Ошибка");
            }
        }

        System.out.println("До свидания!");
        scanner.close();
    }

    private static void init() {
        DomainFactory factory = new DomainFactoryImpl();
        BankAccountRepository accountRepository = new SimpleBankAccountRepository();
        CategoryRepository categoryRepository = new SimpleCategoryRepository();
        OperationRepository operationRepository = new SimpleOperationRepository();
        accountFacade = new BankAccountFacade(accountRepository, factory);
        categoryFacade = new CategoryFacade(categoryRepository, factory);
        operationFacade = new OperationFacade(operationRepository, accountRepository, factory);

        analyticsFacade = new AnalyticsFacade(operationFacade, categoryFacade);
        updateService = new BalanceUpdateService(accountFacade, operationFacade);
        dataExporter = new DataExporter(accountFacade, categoryFacade, operationFacade);

        //Я НЕ МОГУ, ЭТОТ DI НЕ РАБОТАЕТ!!
        /*
        diContainer = DIManager.init();

        accountFacade = diContainer.resolve(BankAccountFacade.class);
        categoryFacade = diContainer.resolve(CategoryFacade.class);
        operationFacade = diContainer.resolve(OperationFacade.class);
        analyticsFacade = diContainer.resolve(AnalyticsFacade.class);
        updateService = diContainer.resolve(BalanceUpdateService.class);
        dataExporter = diContainer.resolve(DataExporter.class);
         */
    }

    private static void basicOps(Scanner scanner) {
        int choice = menuAsk(scanner, "Выберите операцию:", new String[]{
                "Показать все счета",
                "Показать все категории",
                "Показать все операции",
                "Вернуться"
        });
        switch (choice) {
            case 1 -> accountFacade.getBankAccounts().forEach(System.out::println);
            case 2 -> categoryFacade.getCategories().forEach(System.out::println);
            case 3 -> operationFacade.getOperations().forEach(System.out::println);
            case 4 -> {
            }
            default -> System.out.println("Invalid choice");
        }
    }

    private static void commandOps(Scanner scanner) {
        int choice = menuAsk(scanner, "Выберите команду: ", new String[]{
                "Создать операцию",
                "Вывести баланс",
                "Вернуться"
        });
        switch (choice) {
            case 1 -> {
                System.out.print("Введите id: ");
                long id = getLong(scanner);
                System.out.print("Введите сумму: ");
                long sum = getLong(scanner);
                CategoryType type = CategoryType.INCOME;
                if (sum < 0) {
                    sum = -sum;
                    type = CategoryType.EXPENSE;
                }
                System.out.print("Введите id категории: ");
                long catid = getLong(scanner);
                new TimerDecorator(new CreateOperationCommand(operationFacade, type, id, new BigDecimal(sum), "Какая-то операция", catid)).execute();
            }
            case 2 -> {
                System.out.print("Введите id: ");
                long id = getLong(scanner);
                new TimerDecorator(new GetAccountBalanceCommand(accountFacade, id)).execute();
            }
            case 3 -> {}
            default -> System.out.println("Ошибка");
        }
    }

    private static void importOps(Scanner scanner) {
        System.out.println("Введите название файла:");
        String filename = scanner.nextLine();
        DataImporter jsonImporter = new JsonImporter(accountFacade, categoryFacade, operationFacade);
        try {
            jsonImporter.importData(filename);
            System.out.println("Готово!");
        }
        catch (IOException exception) {
            System.out.println("Ошибка импорта: " + exception.getMessage());
        }
    }

    private static void exportOps(Scanner scanner) {
        System.out.println("Введите название файла:");
        String filename = scanner.nextLine();
        DataVisitor jsonVisitor = new JsonExportVisitor();
        try {
            dataExporter.export(filename, jsonVisitor);
            System.out.println("Готово!");
        }
        catch (IOException exception) {
            System.out.println("Ошибка импорта: " + exception.getMessage());
        }
    }

    private static void analyticsOps(Scanner scanner) {
        int choice = menuAsk(scanner, "Выберите команду: ", new String[]{
                "Аналитика по времени",
                "Аналитика по категории",
                "Вернуться"
        });
        switch (choice) {
            case 1 -> {
                System.out.print("Введите количество дней: ");
                long days = getLong(scanner);
                BigDecimal value = analyticsFacade.deltaAnalysis(LocalDateTime.now().minusDays(days), LocalDateTime.now());
                System.out.println("За последние " + days + " дней, суммарное изменение баланса равно " + value);
            }
            case 2 -> {
                boolean income = menuAsk(scanner, "Введите тип категории", new String[] {"Зачисление", "Списание"}) == 1;
                Map<String, BigDecimal> result = analyticsFacade.categoryAnalysis(income ? CategoryType.INCOME : CategoryType.EXPENSE);
                for (Map.Entry<String, BigDecimal> entry : result.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
            case 3 -> {}
            default -> System.out.println("Ошибка");
        }
    }
}