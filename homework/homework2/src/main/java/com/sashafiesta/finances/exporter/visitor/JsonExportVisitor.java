package com.sashafiesta.finances.exporter.visitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.dto.ExportData;
import com.sashafiesta.finances.util.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonExportVisitor implements DataVisitor {
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();
    private final Gson gson;

    public JsonExportVisitor() {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    }

    @Override
    public void visit(BankAccount account) {
        accounts.add(account);
    }

    @Override
    public void visit(Category category) {
        categories.add(category);
    }

    @Override
    public void visit(Operation operation) {
        operations.add(operation);
    }

    @Override
    public String getResult() {
        ExportData exportData = new ExportData(accounts, categories, operations);
        return gson.toJson(exportData);
    }
}