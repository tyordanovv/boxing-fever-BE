package com.boxingfever.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Temporal(TemporalType.DATE)
    @Column(name = "session_date", nullable = false)
    private Date sessionDate;

    @ManyToMany
    @JoinTable(
            name = "session_student",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "session_trainer",
            joinColumns = @JoinColumn(name = "sesion_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "class_id")
    private TrainingClass  aClass;

}
