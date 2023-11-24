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
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register"})
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        String response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}