package com.java.bank.bankingapp.controller;

import com.java.bank.bankingapp.entity.Account;
import com.java.bank.bankingapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/bank")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/addAccount")
    public ResponseEntity<Map<String,String>> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/allAccount")
    public ResponseEntity<List<Account>> getAllAccount() {
        return accountService.getAllAccount();
    }


    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestParam double balance) {
        return accountService.deposit(id,balance);
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestParam double withdrawAmount) {
        return accountService.withdraw(id, withdrawAmount);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }
}
