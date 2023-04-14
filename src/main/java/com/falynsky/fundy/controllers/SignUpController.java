package com.falynsky.fundy.controllers;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.services.AccountService;
import com.falynsky.fundy.utils.ResponseUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("/signUp")
public class SignUpController {

    private final AccountService accountService;

    public SignUpController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> map) {
        try {

            boolean isInvalidRegisterInput = isInvalidRegisterInput(map);
            if (isInvalidRegisterInput) {
                return ResponseUtils.errorResponse("Wartości są wymagane i puste znaki są zabronione.");
            }
            Account newAccount = createAccount(map);
            User newUser = createUser(map);
            accountService.createNewAccountData(newAccount, newUser);
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
        String firstName = (String) map.get("firstName");
        String lastName = (String) map.get("lastName");

        if (username == null || password == null || firstName == null || lastName == null) {
            return true;
        }

        boolean isUsernameValid = isDataValid(username);
        boolean isPasswordValid = isDataValid(password);
        boolean isFirstNameValid = isDataValid(firstName);
        boolean isLastNameValid = isDataValid(lastName);

        return isUsernameValid || isPasswordValid || isFirstNameValid || isLastNameValid;
    }

    private boolean isDataValid(String username) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher usernameMatcher = pattern.matcher(username);
        return usernameMatcher.find();
    }

    private Account createAccount(Map<String, Object> map) {
        Account newAccount = new Account();
        String username = (String) map.get("username");
        newAccount.setUsername(username);
        String password = (String) map.get("password");
        newAccount.setPassword(password);
        String mail = (String) map.get("mail");
        newAccount.setMail(mail);
        return newAccount;
    }

    private User createUser(Map<String, Object> map) {
        User newUser = new User();
        newUser.setFirstName((String) map.get("firstName"));
        newUser.setLastName((String) map.get("lastName"));
        return newUser;
    }

}
