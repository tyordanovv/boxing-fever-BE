package com.boxingfever.api.user;

public record UpdateUserRequest(
        String id,
        String userEmail,
        String firstName,
        String lastName,
        String address

        ) {
}
