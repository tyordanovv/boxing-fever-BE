package com.boxingfever.service;

import com.boxingfever.api.user.LoginDto;
import com.boxingfever.api.user.RegisterRequest;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterRequest registerRequest);
}
