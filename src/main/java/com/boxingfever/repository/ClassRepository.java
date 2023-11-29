package com.boxingfever.repository;

import com.boxingfever.entity.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClassRepository extends JpaRepository<TrainingClass, Long> {
    Optional<TrainingClass> findByClassName(String className);
}
