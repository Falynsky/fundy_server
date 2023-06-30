package com.falynsky.fundy.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falynsky.fundy.models.DTO.AccountDTO;
import com.falynsky.fundy.repositories.AccountRepository;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{id}")
    public AccountDTO getUserById(@PathVariable("id") Integer id) {
        return accountRepository.retrieveAccountAsDTObyId(id);
    }

    @GetMapping("/all")
    public List<AccountDTO> getAllUsers() {
        return accountRepository.retrieveAccountAsDTO();
    }
}
