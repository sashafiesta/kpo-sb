package com.sashafiesta.finances.command.impl;

import com.sashafiesta.finances.command.Command;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.facade.OperationFacade;
import java.math.BigDecimal;

public class CreateOperationCommand implements Command {
    private OperationFacade operationFacade;
    private CategoryType type;
    private long bank_account_id;
    private BigDecimal amount;
    private String name;
    private Long categoryId;

    public CreateOperationCommand(OperationFacade operationFacade, CategoryType type, Long bank_account_id, BigDecimal amount, String name, Long categoryId) {
        this.operationFacade = operationFacade;
        this.type = type;
        this.bank_account_id = bank_account_id;
        this.amount = amount;
        this.name = name;
        this.categoryId = categoryId;
    }
    @Override
    public boolean execute() {
        try {
            operationFacade.createOperation(type, bank_account_id, amount, name, categoryId);
            return true;
        }
        catch (IllegalArgumentException exception) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "Операция  " + type + ", на сумму: " + amount;
    }
}