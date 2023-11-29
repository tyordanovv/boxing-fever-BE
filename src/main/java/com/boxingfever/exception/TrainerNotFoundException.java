package com.boxingfever.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainerNotFoundException extends RuntimeException {

    public TrainerNotFoundException(Long trainerId) {
        super("Trainer with ID " + trainerId + " not found");
    }
}