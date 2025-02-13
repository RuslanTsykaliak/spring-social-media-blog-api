package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import com.example.exception.SocialMediaAPIException;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Username cannot be blank.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters long.");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new SocialMediaAPIException(HttpStatus.CONFLICT, "Username already exists.");
        }

        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword() == null || account.getPassword().isBlank()) {
            throw new SocialMediaAPIException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }

        Optional<Account> storedAccount = accountRepository.findByUsername(account.getUsername());
        
        if (storedAccount.isEmpty()) {
            throw new SocialMediaAPIException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }

        if (!storedAccount.get().getPassword().equals(account.getPassword())) {
            throw new SocialMediaAPIException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }

        return storedAccount.get();
    }
}
