package com.springboot.blog.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record RegisterRequest (
        String firstName,
        String lastName,
        String address,
        String email,
        String password
){
}
