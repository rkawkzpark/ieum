package com.ieum.user.repository;

import com.ieum.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentId(String studentId);


}