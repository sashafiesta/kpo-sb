package com.sashafiesta.shopaholic.payment.controller;

import com.sashafiesta.shopaholic.payment.dto.AccountRequest;
import com.sashafiesta.shopaholic.payment.entity.Account;
import com.sashafiesta.shopaholic.payment.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request.getUserId()));
    }

    @PostMapping("/accounts/deposit")
    public ResponseEntity<Account> deposit(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.addBalance(request.getUserId(), request.getAmount()));
    }

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getBalance(userId));
    }
}
