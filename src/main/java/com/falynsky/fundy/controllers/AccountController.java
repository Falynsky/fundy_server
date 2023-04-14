package com.falynsky.fundy.controllers;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.DTO.AccountDTO;
import com.falynsky.fundy.repositories.AccountRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/accounts")
public class AccountController {

    AccountRepository usersRepository;

    public AccountController(AccountRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public List<AccountDTO> getAllUsers() {
        return usersRepository.retrieveAccountAsDTO();
    }

    @GetMapping("/{id}")
    public AccountDTO getAllUsers(@PathVariable("id") Integer id) {
        return usersRepository.retrieveAccountAsDTObyId(id);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        Optional<Account> userToDelete = usersRepository.findById(userId);
        if (userToDelete.isPresent()) {
            usersRepository.delete(userToDelete.get());
            return "User deleted with id = " + userId;
        } else {
            return String.valueOf(new IOException("User with id = " + userId + " do not exists!"));
        }
    }

}
