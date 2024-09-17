package com.example.userapi.controller;

import com.example.userapi.model.AuthenticationResponse;
import com.example.userapi.model.User;
import com.example.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody User user){
        userService.createUser(user);
    }

    @PostMapping (path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody User user){
        try {
            System.out.println("Authenticating user");
            return ResponseEntity.ok(userService.loginUser(user.getUsername(), user.getPassword()));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
