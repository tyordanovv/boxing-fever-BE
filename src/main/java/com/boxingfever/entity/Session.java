package com.boxingfever.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(name = "session_id")
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

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "session_user",
            joinColumns = { @JoinColumn(name = "session_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "session_trainer",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(
            name = "class_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrainingClass aClass;

    public void addUser(User user){
        users.add(user);
    }

}
