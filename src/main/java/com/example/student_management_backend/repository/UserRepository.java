package com.example.student_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.student_management_backend.domain.User;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User, Integer> {

    public User save(User user);

    public User findByUsername(String username);

    public boolean existsByUsername(String username);

    public User findByUsernameAndPassword(String username, String password);

}
