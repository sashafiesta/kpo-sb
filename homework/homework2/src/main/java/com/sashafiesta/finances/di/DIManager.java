package com.sashafiesta.finances.di;

import com.sashafiesta.finances.analytics.AnalyticsFacade;
import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.domain.Category;
import com.sashafiesta.finances.domain.Operation;
import com.sashafiesta.finances.exporter.DataExporter;
import com.sashafiesta.finances.facade.BankAccountFacade;
import com.sashafiesta.finances.facade.CategoryFacade;
import com.sashafiesta.finances.facade.OperationFacade;
import com.sashafiesta.finances.factory.DomainFactory;
import com.sashafiesta.finances.factory.impl.DomainFactoryImpl;
import com.sashafiesta.finances.repository.BankAccountRepository;
import com.sashafiesta.finances.repository.CategoryRepository;
import com.sashafiesta.finances.repository.OperationRepository;
import com.sashafiesta.finances.repository.Repository;
import com.sashafiesta.finances.repository.impl.SimpleBankAccountRepository;
import com.sashafiesta.finances.repository.impl.SimpleCategoryRepository;
import com.sashafiesta.finances.repository.impl.SimpleOperationRepository;
import com.sashafiesta.finances.repository.impl.SimpleRepository;
import com.sashafiesta.finances.service.BalanceUpdateService;

public class DIManager {
    //Ничего не работает
    /*
    public static DIContainer init() {
        DIContainer container = new DIContainer();
        container.registerSingleton(DomainFactory.class, DomainFactoryImpl.class);
        container.registerSingleton(BankAccountRepository.class, SimpleBankAccountRepository.class);
        container.registerSingleton(CategoryRepository.class, SimpleCategoryRepository.class);
        container.registerSingleton(OperationRepository.class, SimpleOperationRepository.class);
        container.registerSingleton(BankAccountFacade.class, BankAccountFacade.class);
        container.registerSingleton(CategoryFacade.class, CategoryFacade.class);
        container.registerSingleton(OperationFacade.class, OperationFacade.class);
        container.registerSingleton(AnalyticsFacade.class, AnalyticsFacade.class);
        container.registerSingleton(BalanceUpdateService.class, BalanceUpdateService.class);

        container.registerSingleton(DataExporter.class, DataExporter.class);

        return container;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<Repository<T>> getRepositoryClass(Class<T> entityClass) {
        return (Class<Repository<T>>) (Class<?>) Repository.class;
    }
     */
}