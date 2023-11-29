package com.boxingfever.api.classes;

import com.boxingfever.entity.Session;
import com.boxingfever.entity.Trainer;
import com.boxingfever.entity.TrainingClass;
import com.boxingfever.types.TrainingClassEnums;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewClassRequest {

    private String className;

    private String place;

    private int durationInMinutes;

    private String description;

    private String category;

    private Set<Long> trainers;

}
