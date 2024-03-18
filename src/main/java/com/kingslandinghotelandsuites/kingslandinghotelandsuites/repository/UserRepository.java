package com.kingslandinghotelandsuites.kingslandinghotelandsuites.repository;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<Object> findByEmail(String email);
}
