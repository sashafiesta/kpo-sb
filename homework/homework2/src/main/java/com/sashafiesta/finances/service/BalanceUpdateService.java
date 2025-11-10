package com.sashafiesta.finances.service;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BalanceUpdateService {
    private BankAccountFacade accountFacade;
    private OperationFacade operationFacade;

    public BalanceUpdateService(BankAccountFacade accountFacade, OperationFacade operationFacade) {
        this.accountFacade = accountFacade;
        this.operationFacade = operationFacade;
    }

    public boolean UpdateBalance(long id) {
        Optional<BankAccount> result = accountFacade.getBankAccount(id);
        BankAccount account = result.orElseThrow(() -> new IllegalArgumentException("bank account not found"));
        List<Operation> operations = operationFacade.getOperations(account.getId());

        BigDecimal answer = BigDecimal.ZERO;
        for (Operation operation : operations) {
            answer = answer.add(operation.getType() == CategoryType.INCOME ? operation.getAmount() : operation.getAmount().negate());
        }
        if (!account.getBalance().equals(answer)) {
            accountFacade.updateBalance(id, answer);
            return true;
        }
        return false;
    }

    public long UpdateBalances() {
        List<BankAccount> accounts = accountFacade.getBankAccounts();
        long quantity = 0;
        for (BankAccount account : accounts) {
            if (UpdateBalance(account.getId())) {
                quantity++;
            }
        }
        return quantity;
    }
}
