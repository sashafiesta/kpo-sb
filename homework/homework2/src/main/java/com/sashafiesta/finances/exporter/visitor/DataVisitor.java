package com.sashafiesta.finances.exporter.visitor;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;

public interface DataVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
    String getResult();
}