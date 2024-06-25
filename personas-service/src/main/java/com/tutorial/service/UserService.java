package com.tutorial.service;
import com.tutorial.entity.User;
import com.tutorial.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> listUser(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public Optional<User> getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Optional<User> getByTokenPassword(String tokenPassword){
        return userRepository.findByTokenPassword(tokenPassword);
    }
    public Optional<User> getByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail);
    }

    public boolean existsById(int id){
        return userRepository.existsById(id);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User user){
        userRepository.save(user);
    }
}