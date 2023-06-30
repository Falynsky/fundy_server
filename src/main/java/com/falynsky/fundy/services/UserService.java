package com.falynsky.fundy.services;

import com.falynsky.fundy.JWT.utils.JwtTokenUtil;
import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.repositories.AccountRepository;
import com.falynsky.fundy.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(
        UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder, 
            JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User getCurrentUser(String userToken) throws Exception {
        String currentUserUsername = jwtTokenUtil.getUsernameFromToken(userToken.substring(5));

        User user = userRepository.findByUsername(currentUserUsername);
        if (user == null) {
            throw new Exception("USER NOT FOUND");
        }
        return user;
    }

    public void createNewUserData(User user, Account account) {
        createNewUser(user);
        createNewAccount(user, account);
    }

    private void createNewUser(User user) {
        String encodePassword = getEncodedPassword(user);
        user.setPassword(encodePassword);

        Integer newAccountId = getIdForNewUser();
        user.setId(newAccountId);
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    private String getEncodedPassword(User account) {
        String password = account.getPassword();
        return passwordEncoder.encode(password);
    }

    private Integer getIdForNewUser() {
        User lastUser = userRepository.findFirstByOrderByIdDesc();
        if (lastUser == null) {
            return 1;
        }
        Integer lastId = lastUser.getId();
        return ++lastId;
    }

    private void createNewAccount(User user, Account account) {
        int newUserId = getIdForNewAccount();
        account.setId(newUserId);
        account.setUserId(user);
        account.setUserId(user);
        account.setAccountBalance(0);
        accountRepository.save(account);
    }

    private int getIdForNewAccount() {
        Account lastAccount = accountRepository.findFirstByOrderByIdDesc();
        if (lastAccount == null) {
            return 1;
        }
        int lastId = lastAccount.getId();
        return ++lastId;
    }

}
