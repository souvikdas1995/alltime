package com.domjur.alltime.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String emailId;
    private String password;
    private String firstname;
    private String lastName;
    private String group;
}
