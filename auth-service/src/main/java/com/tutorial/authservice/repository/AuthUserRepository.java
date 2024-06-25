package com.tutorial.authservice.repository;

import com.tutorial.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserNameOrEmail(String userName, String email);
}
