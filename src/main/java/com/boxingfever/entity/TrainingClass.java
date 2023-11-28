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
    @Column(name = "class_id")
    private Long id;

    @Column(name = "class_name", nullable = false)
    private String name;

    @Column(name = "adress", nullable = true)
    private String place;

    @Column(name = "duration", nullable = false)
    private int durationInMinutes;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainingClassEnums category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "class_trainer",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();


}
