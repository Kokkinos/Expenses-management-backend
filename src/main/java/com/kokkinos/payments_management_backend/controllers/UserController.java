package com.kokkinos.payments_management_backend.controllers;

import com.kokkinos.payments_management_backend.dtos.AuthResponseDTO;
import com.kokkinos.payments_management_backend.entities.User;
import com.kokkinos.payments_management_backend.services.JwtService;
import com.kokkinos.payments_management_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            Date exp = jwtService.extractExpiration(token);
            return new AuthResponseDTO(token, exp.getTime());
        } else {
            throw new RuntimeException("Login failed!");
        }
    }

}
