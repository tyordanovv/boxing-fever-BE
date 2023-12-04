package com.boxingfever.api.trainer;

public record UpdateTrainerRequest(
        long id,
        String email,
        String name

        ) {
}
