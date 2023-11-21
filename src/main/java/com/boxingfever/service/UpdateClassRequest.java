package com.boxingfever.service;

import com.boxingfever.entity.Trainer;
import com.boxingfever.types.TrainingClassEnums;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateClassRequest {
    private String newClassName;
    private int durationInMinutes;
    private String place;
    private String description;
    private TrainingClassEnums category;
    private Set<Trainer> trainers = new HashSet<>();
}