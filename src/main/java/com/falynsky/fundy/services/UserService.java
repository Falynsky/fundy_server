package com.falynsky.fundy.services;

import com.falynsky.fundy.models.Account;
import com.falynsky.fundy.models.User;
import com.falynsky.fundy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getCurrentUserByAccount(Account account) throws Exception {
        User user = userRepository.findFirstByAccountId(account);
        if (user == null) {
            throw new Exception("USER NOT FOUND");
        }
        return user;
    }

}
