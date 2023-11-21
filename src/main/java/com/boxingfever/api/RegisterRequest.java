package com.boxingfever.api;
public record RegisterRequest (
        String firstName,
        String lastName,
        String address,
        String email,
        String password
){
}