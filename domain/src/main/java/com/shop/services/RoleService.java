package com.shop.services;

import com.shop.models.Role;
import com.shop.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        return role.orElseGet(Role::new);
    }
}
