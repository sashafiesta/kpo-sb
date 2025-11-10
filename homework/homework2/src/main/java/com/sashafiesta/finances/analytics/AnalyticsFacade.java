package com.sashafiesta.finances.analytics;

import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsFacade {
    private OperationFacade operationFacade;
    private CategoryFacade categoryFacade;

    public AnalyticsFacade(OperationFacade operationFacade, CategoryFacade categoryFacade) {
        this.operationFacade = operationFacade;
        this.categoryFacade = categoryFacade;
    }


    private boolean isBetween(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public BigDecimal deltaAnalysis(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getOperations().stream().filter(operation -> isBetween(operation.getDate(), start, end)).toList();
        BigDecimal profit = operations.stream().filter(operation -> operation.getType() == CategoryType.INCOME).map(Operation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal loss = operations.stream().filter(operation -> operation.getType() == CategoryType.EXPENSE).map(Operation::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return profit.subtract(loss);
    }

    public Map<String, BigDecimal> categoryAnalysis(CategoryType type) {
        Map<String, BigDecimal> result = new HashMap<>();
        List<Operation> operations = operationFacade.getOperations().stream().filter(op -> op.getType() == type).toList();
        for (Operation operation : operations) {
            Category category = categoryFacade.getCategory(operation.getCategoryId()).orElse(null);
            if (category != null) {
                result.merge(category.getName(), operation.getAmount(), BigDecimal::add);
            }
        }
        return result;
    }

    public void printCategoryReport(CategoryType type) {
        System.out.println("Report by " + type.name());
        Map<String, BigDecimal> data = categoryAnalysis(type);
        for (Map.Entry<String, BigDecimal> things : data.entrySet()) {
            System.out.println(things.getKey() + ": " + things.getValue());
        }
        System.out.println("Total: " + data.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void printDeltaReport(LocalDateTime start, LocalDateTime end) {
        System.out.println("Report from " + start + " to " + end);
        System.out.println("Difference: " + deltaAnalysis(start, end));
    }
}
