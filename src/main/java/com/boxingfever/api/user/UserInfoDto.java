package com.boxingfever.api.user;

import java.util.Set;

public record UserInfoDto(
        Long id,
        String firstName,
        String lastName,
        String address,
        String email,
        Set<String> roleName
) {}
