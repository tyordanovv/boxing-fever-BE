package com.boxingfever.api.user;

public record UpdateUserRequest(
        String id,
        String email,
        String firstName,
        String lastName,
        String address

        ) {
}
