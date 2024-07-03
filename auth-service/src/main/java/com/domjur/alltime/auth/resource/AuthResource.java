package com.domjur.alltime.auth.resource;

import com.domjur.alltime.auth.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class AuthResource {


    @GetMapping
    public List<User> getAllEmployees() {
        return Arrays.asList(new User("My App User"));
    }
}
