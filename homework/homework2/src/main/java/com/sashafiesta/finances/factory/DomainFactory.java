package com.sashafiesta.finances.factory;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.CategoryType;
import com.sashafiesta.finances.domain.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface DomainFactory {
    BankAccount createBankAccount(long id, String name, BigDecimal balance) throws IllegalArgumentException;
    Category createCategory(long id, String name, CategoryType type) throws IllegalArgumentException;
    Operation createOperation(long id, CategoryType type, long bank_account_id, BigDecimal amount, LocalDateTime date, String description, long category_id) throws IllegalArgumentException;
}
