package com.sashafiesta.finances.importer;

import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;

import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class DataImporter {
    protected final BankAccountFacade accountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    public DataImporter(BankAccountFacade accountFacade, CategoryFacade categoryFacade, OperationFacade operationFacade) {
        this.accountFacade = accountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    public final void importData(String filePath) throws IOException {
        System.out.println("Importing from: " + filePath);
        String content = Files.readString(Path.of(filePath));
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        processData(content);
        System.out.println("Importing finished.");
    }

    protected abstract void processData(String data);
    protected abstract String getFormatName();
}