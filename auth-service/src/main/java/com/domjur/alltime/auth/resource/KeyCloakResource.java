package com.domjur.alltime.auth.resource;

import com.domjur.alltime.auth.dto.UserDto;
import com.domjur.alltime.auth.response.UserResponse;
import com.domjur.alltime.auth.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "api/user")
public class KeyCloakResource {

    @Autowired
    KeycloakService service;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserDto userDto){
       UserResponse userResponse= service.addUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userName}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userName") String userName){
        UserResponse user = service.getUser(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*@PutMapping(path = "/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto){
        service.updateUser(userId, userDto);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }*/
}
