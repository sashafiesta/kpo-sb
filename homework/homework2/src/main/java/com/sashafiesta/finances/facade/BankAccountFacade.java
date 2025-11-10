package com.sashafiesta.finances.facade;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.factory.DomainFactory;
import com.sashafiesta.finances.repository.BankAccountRepository;
import com.sashafiesta.finances.repository.Repository;
import com.sashafiesta.finances.repository.impl.SimpleBankAccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BankAccountFacade {
    private BankAccountRepository repository;
    private DomainFactory factory;
    private long idGen;

    public BankAccountFacade(BankAccountRepository repository, DomainFactory factory) {
        this.repository = repository;
        this.factory = factory;
        this.idGen = 1;
    }

    public BankAccount createBankAccount(String name, BigDecimal balance) throws IllegalArgumentException {
        BankAccount account = factory.createBankAccount(idGen++, name, balance);
        repository.save(account);
        return account;
    }

    public Optional<BankAccount> getBankAccount(long id) {
        return repository.find(id);
    }

    public List<BankAccount> getBankAccounts() {
        return repository.findAll();
    }

    public void updateBankAccount(long id, String name) throws IllegalArgumentException {
        Optional<BankAccount> result = repository.find(id);
        BankAccount account = result.orElseThrow(() -> new IllegalArgumentException("bank account not found"));
        account.setName(name);
        repository.update(account);
    }

    public void updateBalance(long id, BigDecimal balance) throws IllegalArgumentException {
        Optional<BankAccount> result = repository.find(id);
        BankAccount account = result.orElseThrow(() -> new IllegalArgumentException("bank account not found"));
        account.setBalance(balance);
        repository.update(account);
    }

    public void deleteBankAccount(long id) {
        repository.delete(id);
    }
}
