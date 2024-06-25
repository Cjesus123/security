package com.tutorial.repository;
import com.tutorial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findById(Integer id);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameOrEmail(String userName, String email);
    Optional<User> findByTokenPassword(String tokenPassword);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    void deleteById(Integer id);
    List<User> findAll();
}
