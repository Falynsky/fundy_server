package com.falynsky.fundy.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
   
    private int id;
    private String login;
    private String password;
    private String mail;
    private String role;
}

