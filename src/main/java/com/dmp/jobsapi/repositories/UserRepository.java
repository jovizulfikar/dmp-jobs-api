package com.dmp.jobsapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.dmp.jobsapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(@Param("username") String username);
}
