package com.sashafiesta.finances.command.impl;


import com.sashafiesta.finances.command.Command;
import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.facade.BankAccountFacade;

import java.util.Optional;

public class GetAccountBalanceCommand implements Command {
    private final BankAccountFacade accountFacade;
    private final Long accountId;

    public GetAccountBalanceCommand(BankAccountFacade accountFacade, Long accountId) {
        this.accountFacade = accountFacade;
        this.accountId = accountId;
    }

    @Override
    public boolean execute() {
        try {
            Optional<BankAccount> result = accountFacade.getBankAccount(accountId);
            BankAccount account = result.orElseThrow(() -> new IllegalArgumentException("Account not found"));
            System.out.println("Account: " + account.getName() + " with balance: " + account.getBalance());
            return true;
        }
        catch (IllegalArgumentException exception) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "Запрос баланса на счете " + accountId;
    }
}
