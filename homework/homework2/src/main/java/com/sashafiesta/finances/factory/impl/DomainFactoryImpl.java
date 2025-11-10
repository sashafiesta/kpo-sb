package com.sashafiesta.finances.factory.impl;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.factory.DomainFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DomainFactoryImpl implements DomainFactory {

    @Override
    public BankAccount createBankAccount(long id, String name, BigDecimal balance) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (balance == null) {
            throw new IllegalArgumentException("balance must be non-null");
        }
        return new BankAccount(id, name, balance);
    }

    @Override
    public Category createCategory(long id, String name, CategoryType type) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("name must be non-null");
        }
        return new Category(id, name, type);
    }

    @Override
    public Operation createOperation(long id, CategoryType type, long bank_account_id, BigDecimal amount, LocalDateTime date, String description, long category_id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        if (type == null) {
            throw new IllegalArgumentException("type must be non-null");
        }
        if (bank_account_id <= 0) {
            throw new IllegalArgumentException("bank_account_id must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("date must be non-null");
        }
        if (category_id <= 0) {
            throw new IllegalArgumentException("category_id must be positive");
        }
        return new Operation(id, type, bank_account_id, amount, date, description, category_id);
    }
}
