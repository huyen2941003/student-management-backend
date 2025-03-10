package com.example.student_management_backend.service;

import java.security.Permission;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.example.student_management_backend.domain.Role;
import com.example.student_management_backend.repository.RoleRepository;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean existByName(String roleName) {
        return this.roleRepository.existsByName(roleName);
    }

    public Role create(Role r) {
        return this.roleRepository.save(r);
    }

    public Role fetchById(long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (roleOptional.isPresent())
            return roleOptional.get();
        return null;
    }

    public Role update(Role r) {
        Role roleDB = this.fetchById(r.getId());

        roleDB.setRoleName(r.getRoleName());
        roleDB.setPermissions(r.getPermissions());
        roleDB = this.roleRepository.save(roleDB);
        return roleDB;
    }

    public void delete(long id) {
        this.roleRepository.deleteById(id);
    }
}
