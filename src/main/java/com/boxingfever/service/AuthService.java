package com.boxingfever.service;

import com.boxingfever.api.LoginDto;
import com.boxingfever.api.RegisterRequest;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterRequest registerRequest);
}
