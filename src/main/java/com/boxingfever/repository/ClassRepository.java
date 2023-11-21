package com.boxingfever.repository;

import com.boxingfever.entity.Role;
import com.boxingfever.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<TrainingClass, Long> {
    Optional<Class> findByName(String name);
}
