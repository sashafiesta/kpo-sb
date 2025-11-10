package com.sashafiesta.finances.exporter;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.exporter.visitor.DataVisitor;
import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class DataExporter {
    private final BankAccountFacade accountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

    public DataExporter(BankAccountFacade accountFacade, CategoryFacade categoryFacade, OperationFacade operationFacade) {
        this.accountFacade = accountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    public void export(String filePath, DataVisitor visitor) throws IOException {
        System.out.println("Exporting to: " + filePath);
        for (BankAccount account : accountFacade.getBankAccounts()) {
            visitor.visit(account);
        }
        for (Category category : categoryFacade.getCategories()) {
            visitor.visit(category);
        }
        for (Operation operation : operationFacade.getOperations()) {
            visitor.visit(operation);
        }
        String content = visitor.getResult();
        Files.writeString(Path.of(filePath), content);
        System.out.println("Exporting finished");
    }
}
