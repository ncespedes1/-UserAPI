package com.example.userapi.service;

import com.example.userapi.model.AuthenticationResponse;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private JwtService jwtService;

    @Autowired
    public UserService (UserRepository userRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // make a method called getUsers that returns a list of users
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public AuthenticationResponse loginUser(String username, String password){
        Optional<User> userModelOptional = userRepository.findUserByUsernameAndPassword(username, password);
        if (userModelOptional.isPresent()){
            User user = userModelOptional.get();
            return mapToAuthenticationResponse(user);
            // good login
        } else {
            // bad login
            throw new IllegalStateException("Incorrect login");
        }
    }

    private AuthenticationResponse mapToAuthenticationResponse(User user){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setId(user.getId());
        authenticationResponse.setFirstName(user.getFirstName());
        authenticationResponse.setLastName(user.getLastName());
        authenticationResponse.setUsername(user.getUsername());
        authenticationResponse.setToken(jwtService.generateToken(user));
        return authenticationResponse;
    }

}
