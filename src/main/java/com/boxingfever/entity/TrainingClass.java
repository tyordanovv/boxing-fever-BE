package com.boxingfever.entity;

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
@Entity
@Builder
@Table(name = "classes")
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String place;

    private int durationInMinutes;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    private TrainingClassEnums category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "class_trainer",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();


}
