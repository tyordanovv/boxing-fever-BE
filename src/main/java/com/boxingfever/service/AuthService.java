package com.boxingfever.service;

import com.boxingfever.api.user.LoginDto;
import com.boxingfever.api.user.RegisterRequest;
import com.boxingfever.api.util.JWTAuthResponse;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    void register(RegisterRequest registerRequest);
}
