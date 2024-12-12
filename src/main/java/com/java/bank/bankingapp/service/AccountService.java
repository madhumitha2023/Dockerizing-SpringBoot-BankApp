package com.java.bank.bankingapp.service;

import com.java.bank.bankingapp.entity.Account;
import com.java.bank.bankingapp.exception.InvalidAccountException;
import com.java.bank.bankingapp.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

   private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Map<String,String>> createAccount(Account account) {
        accountRepository.save(account);
        Map<String, String> response = new HashMap<>();
        response.put(account.getAccountHolderName(),"Account Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<List<Account>> getAllAccount() {
      return ResponseEntity.ok(accountRepository.findAll());
    }

    public ResponseEntity<Account> getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }
        else{
            throw new InvalidAccountException("Account not found with Id: "+ id);
        }
    }

    public ResponseEntity<Account> deposit(Long id, double balance) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Account dbAccount = account.get();
            dbAccount.setBalance(dbAccount.getBalance()+balance);
            Account savedAccount = accountRepository.save(dbAccount);
            return ResponseEntity.ok(savedAccount);
        }
        else{
            throw new InvalidAccountException("Account not found with Id: "+ id);
        }
    }

    public ResponseEntity<?> withdraw(Long id, double withdrawAmount) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Account dbAccount = account.get();
            if(withdrawAmount < dbAccount.getBalance()){
                dbAccount.setBalance(dbAccount.getBalance()-withdrawAmount);
                accountRepository.save(dbAccount);
                Map<String,String> response = new HashMap<>();
                response.put(dbAccount.getAccountHolderName(),"Amount Withdrawn is: "+ withdrawAmount+ " and your balance is: "+ dbAccount.getBalance());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else{
                throw new InvalidAccountException("Insufficient Balance");
            }
        }
        else{
            throw new InvalidAccountException("Account not found with Id: "+ id);
        }
    }

    public ResponseEntity<String> deleteAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            return ResponseEntity.status(HttpStatus.OK).body("Account with Id:" + id + " deleted successfully");
        }
        else{
            throw new InvalidAccountException("Account not found with Id: "+ id);
        }
    }
}
