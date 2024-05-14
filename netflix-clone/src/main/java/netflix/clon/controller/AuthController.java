package com.netflix.clon.controller;

import com.netflix.clon.dto.LoginRequest;
import com.netflix.clon.dto.LoginResponse;
import com.netflix.clon.dto.RegisterRequest;
import com.netflix.clon.model.User;
import com.netflix.clon.security.jwt.JwtTokenProvider;
import com.netflix.clon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        // Check if username already exists
        if (userService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        // Create new user
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setAdmin(false); // Assuming new users are not admins by default
        User createdUser = userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
