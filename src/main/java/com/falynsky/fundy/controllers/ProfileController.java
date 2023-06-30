package com.falynsky.fundy.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falynsky.fundy.models.User;
import com.falynsky.fundy.services.UserService;
import com.falynsky.fundy.utils.ResponseMapUtils;

@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(
            UserService userService) {
        this.userService = userService;
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/getInfo")
    public Map<String, Object> getProfileInfo(
            @RequestHeader(value = "Auth", defaultValue = "empty") String userToken) {
        Map<String, Object> data = new HashMap<>();
        try {
            User currentUser = getCurrentUser(userToken);
            data.put("username", currentUser.getUsername());
            data.put("userId", currentUser.getId());
        } catch (Exception ex) {
            return ResponseMapUtils.buildResponse(data, false);
        }
        return ResponseMapUtils.buildResponse(data, true);
    }

    private User getCurrentUser(String userToken) throws Exception {
        return userService.getCurrentUser(userToken);
    }

}
