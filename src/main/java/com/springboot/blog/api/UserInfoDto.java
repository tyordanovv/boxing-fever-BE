package com.springboot.blog.api;

import java.util.Set;

public record UserInfoDto(
        String firstName,
        String lastName,
        String address,
        String email,
        Set<String> roleName
) {}
