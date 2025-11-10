package com.sashafiesta.finances.facade;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.factory.DomainFactory;
import com.sashafiesta.finances.repository.BankAccountRepository;
import com.sashafiesta.finances.repository.OperationRepository;
import com.sashafiesta.finances.repository.Repository;
import com.sashafiesta.finances.repository.impl.SimpleBankAccountRepository;
import com.sashafiesta.finances.repository.impl.SimpleOperationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OperationFacade {
    private OperationRepository repository;
    private BankAccountRepository accounts;
    private DomainFactory factory;
    private long idGen;

    public OperationFacade(OperationRepository repository, BankAccountRepository accounts, DomainFactory factory) {
        this.repository = repository;
        this.accounts = accounts;
        this.factory = factory;
        this.idGen = 1;
    }

    public Operation createOperation(CategoryType type, long bank_account_id, BigDecimal amount, String description, long category_id) throws IllegalArgumentException {
        Operation operation = factory.createOperation(idGen++, type, bank_account_id, amount, LocalDateTime.now(), description, category_id);
        repository.save(operation);

        Optional<BankAccount> result = accounts.find(bank_account_id);
        BankAccount account = result.orElseThrow(() -> new IllegalArgumentException("bank account not found"));
        if (type == CategoryType.INCOME) {
            account.increaseBalance(amount);
        } else if (type == CategoryType.EXPENSE) {
            account.decreaseBalance(amount);
        }
        return operation;
    }

    public Optional<Operation> getOperation(long id) {
        return repository.find(id);
    }

    public List<Operation> getOperations() {
        return repository.findAll();
    }

    public List<Operation> getOperations(long bank_account_id) {
        return repository.findAll().stream().filter(operation -> operation.getBankAccountId() == bank_account_id).toList();
    }

    public void deleteOperation(long id) throws IllegalArgumentException {
        Optional<Operation> result = repository.find(id);
        Operation operation = result.orElseThrow(() -> new IllegalArgumentException("operation not found"));
        Optional<BankAccount> accountResult = accounts.find(operation.getBankAccountId());
        BankAccount account = accountResult.orElseThrow(() -> new IllegalArgumentException("bank account not found"));

        if (operation.getType() == CategoryType.INCOME) {
            account.decreaseBalance(operation.getAmount());
        } else if (operation.getType() == CategoryType.EXPENSE) {
            account.increaseBalance(operation.getAmount());
        }
        accounts.update(account);
        repository.delete(id);
    }
}
