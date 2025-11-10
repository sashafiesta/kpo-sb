package com.sashafiesta.finances.importer.impl;

import com.google.gson.JsonSyntaxException;
import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.dto.ExportData;
import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sashafiesta.finances.importer.DataImporter;

public class JsonImporter extends DataImporter {
    private final Gson gson;

    public JsonImporter(BankAccountFacade accountFacade, CategoryFacade categoryFacade, OperationFacade operationFacade) {
        super(accountFacade, categoryFacade, operationFacade);
        this.gson = new GsonBuilder().serializeNulls().create();
    }

    @Override
    protected void processData(String content) {
        try {
            ExportData data = gson.fromJson(content, ExportData.class);

            if (data == null) {
                throw new IllegalArgumentException("Invalid JSON format");
            }

            if (data.getAccounts() != null) {
                for (BankAccount account : data.getAccounts()) {
                    accountFacade.createBankAccount(account.getName(), account.getBalance());
                }
            }
            if (data.getCategories() != null) {
                for (Category category : data.getCategories()) {
                    categoryFacade.createCategory(category.getName(), category.getType());
                }
            }

            if (data.getOperations() != null) {
                for (Operation operation : data.getOperations()) {
                    operationFacade.createOperation(operation.getType(), operation.getBankAccountId(), operation.getAmount(), operation.getDescription(), operation.getCategoryId());
                }
            }

        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Invalid JSON syntax: " + e.getMessage(), e);
        }
    }

    @Override
    protected String getFormatName() {
        return "JSON";
    }
}