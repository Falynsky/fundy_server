package com.falynsky.fundy.controllers;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.services.UserService;
import com.falynsky.fundy.utils.ResponseUtils;

@CrossOrigin
@RestController
@RequestMapping("/signUp")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> map) {
        try {

            boolean isInvalidRegisterInput = isInvalidRegisterInput(map);
            if (isInvalidRegisterInput) {
                return ResponseUtils.errorResponse("Wartości są wymagane i puste znaki są zabronione.");
            }
            User newUser = createUser(map);
            Account newAccount = createAccount(map);
            userService.createNewUserData(newUser, newAccount);
        } catch (DataIntegrityViolationException e) {
            return ResponseUtils.errorResponse("Podany użytkownik już istnieje.");
        } catch (Exception e) {
            return ResponseUtils.errorResponse("Błąd serwera!", "Spróbuj ponownie później.");
        }
        return ResponseUtils.sendCorrectResponse();
    }

    private boolean isInvalidRegisterInput(Map<String, Object> map) {

        String username = ((String) map.get("username"));
        String password = (String) map.get("password");
        String mail = (String) map.get("mail");

        if (username == null || password == null || mail == null) {
            return true;
        }

        boolean isUsernameValid = isDataValid(username);
        boolean isPasswordValid = isDataValid(password);
        boolean isNameValid = isDataValid(mail);

        return isUsernameValid || isPasswordValid || isNameValid;
    }

    private boolean isDataValid(String username) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher usernameMatcher = pattern.matcher(username);
        return usernameMatcher.find();
    }

    private User createUser(Map<String, Object> map) {
        User newUser = new User();
        String username = (String) map.get("username");
        newUser.setUsername(username);
        String password = (String) map.get("password");
        newUser.setPassword(password);
        String mail = (String) map.get("mail");
        newUser.setMail(mail);
        return newUser;
    }

    private Account createAccount(Map<String, Object> map) {
        Account newAccount = new Account();
        newAccount.setName((String) map.get("name"));
        return newAccount;
    }

}
