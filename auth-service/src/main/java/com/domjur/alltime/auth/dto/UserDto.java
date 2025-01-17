package com.domjur.alltime.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;
    private String group;
}
