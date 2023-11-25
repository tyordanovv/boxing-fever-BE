package com.boxingfever.controller;

import com.boxingfever.api.util.JWTAuthResponse;
import com.boxingfever.api.user.LoginDto;
import com.boxingfever.api.user.RegisterRequest;
import com.boxingfever.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        JWTAuthResponse response = authService.login(loginDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register"})
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}