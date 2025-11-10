package com.sashafiesta.finances.dto;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;

import java.util.List;

public class ExportData {
    private List<BankAccount> accounts;
    private List<Category> categories;
    private List<Operation> operations;

    public ExportData() {
    }

    public ExportData(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        this.accounts = accounts;
        this.categories = categories;
        this.operations = operations;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}