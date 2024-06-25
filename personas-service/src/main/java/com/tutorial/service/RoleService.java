package com.tutorial.service;

import com.tutorial.entity.Role;
import com.tutorial.enums.RoleName;
import com.tutorial.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> findByNombre(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
}
