package com.sashafiesta.finances.repository.impl;

import com.sashafiesta.finances.domain.BankAccount;
import com.sashafiesta.finances.repository.BankAccountRepository;

public class SimpleBankAccountRepository extends SimpleRepository<BankAccount> implements BankAccountRepository {
    public SimpleBankAccountRepository() {
        super(BankAccount::getId);
    }
}
