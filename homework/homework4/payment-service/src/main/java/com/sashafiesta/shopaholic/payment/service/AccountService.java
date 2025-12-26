package com.sashafiesta.shopaholic.payment.service;

import com.sashafiesta.shopaholic.payment.entity.Account;
import com.sashafiesta.shopaholic.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Long userId) {
        if (accountRepository.existsById(userId)) {
            throw new RuntimeException("Account already exists for user: " + userId);
        }
        Account account = Account.builder().userId(userId).balance(BigDecimal.ZERO).build();
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(Long userId) {
        return accountRepository.findById(userId).map(Account::getBalance).orElseThrow(() -> new RuntimeException("Account not found for user: " + userId));
    }

    @Transactional
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public Account addBalance(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        Account account = accountRepository.findById(userId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public boolean debit(Long userId, BigDecimal amount) {
        Account account = accountRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("Account not found"));
             
        if (account.getBalance().compareTo(amount) < 0) {
            return false;
        }
        
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        return true;
    }
}
