package com.boxingfever.api.user;

public record LoginDto(
        String email,
        String password
) {}
