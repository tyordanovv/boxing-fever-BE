package com.boxingfever.api.trainer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record CreateTrainerRequest (
        String name,
        String email
)
{}
