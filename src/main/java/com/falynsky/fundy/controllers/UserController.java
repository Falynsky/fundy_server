package com.falynsky.fundy.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falynsky.fundy.models.User;
import com.falynsky.fundy.models.DTO.UserDTO;
import com.falynsky.fundy.repositories.UserRepository;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/users")
public class UserController {

    UserRepository usersRepository;

    public UserController(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return usersRepository.retrieveUserAsDTO();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Integer id) {
        return usersRepository.retrieveUserAsDTObyId(id);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        Optional<User> userToDelete = usersRepository.findById(userId);
        if (userToDelete.isPresent()) {
            usersRepository.delete(userToDelete.get());
            return "User deleted with id = " + userId;
        } else {
            return String.valueOf(new IOException("User with id = " + userId + " do not exists!"));
        }
    }

}
