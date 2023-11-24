package com.boxingfever.api.user;
public record RegisterRequest (
        String firstName,
        String lastName,
        String address,
        String email,
        String password
){
}