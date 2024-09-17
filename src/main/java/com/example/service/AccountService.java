package com.example.service;

import com.example.exception.*;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Boolean getAccountByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsername(username);
        if(optionalAccount.isPresent()) {
            return true;
        }
        return false;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findAccountByUsernameAndPassword(username, password);
        if(optionalAccount.isPresent()) {
            return new Account(optionalAccount.get().getAccountId(), optionalAccount.get().getUsername(), optionalAccount.get().getPassword());
        }
        return null;
    }

    public Account existUser(Integer id) {
        return accountRepository.findById(id).get();
    }

    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }
}
