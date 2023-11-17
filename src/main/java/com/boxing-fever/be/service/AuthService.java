package com.springboot.blog.service;

import com.springboot.blog.api.LoginDto;
import com.springboot.blog.api.RegisterRequest;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterRequest registerRequest);
}
