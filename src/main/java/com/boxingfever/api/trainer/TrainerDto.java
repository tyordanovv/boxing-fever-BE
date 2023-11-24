package com.boxingfever.api.trainer;

import jakarta.validation.constraints.NotNull;

public record TrainerDto (
        Long id,
        String name
) {}
