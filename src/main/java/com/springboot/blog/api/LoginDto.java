package com.springboot.blog.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record LoginDto(
        String email,
        String password
) {}
