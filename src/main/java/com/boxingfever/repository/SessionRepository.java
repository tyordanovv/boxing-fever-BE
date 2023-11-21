package com.boxingfever.repository;

import com.boxingfever.entity.Role;
import com.boxingfever.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByName(String name);
}
