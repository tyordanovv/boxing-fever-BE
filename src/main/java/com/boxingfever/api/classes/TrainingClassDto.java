package com.boxingfever.api.classes;

import com.boxingfever.entity.Trainer;
import com.boxingfever.types.TrainingClassEnums;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingClassDto {


    private Long id;
    private String name;

    private String place;

    private int durationInMinutes;

    private String description;

    private TrainingClassEnums category;
    private Set<Long> trainers = new HashSet<>();

}
