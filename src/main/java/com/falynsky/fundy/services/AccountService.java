package com.falynsky.fundy.services;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.repositories.AccountRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getCurrentUserByAccount(User user) throws Exception {
        List<Account> account = accountRepository.findAllByUserId(user);
        if (account.isEmpty()) {
            throw new Exception("ACCOUNTS NOT FOUND");
        }
        return account;
    }

}
