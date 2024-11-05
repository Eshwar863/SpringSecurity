package com.SpringDemo.SpringSecurity.Repo;

import com.SpringDemo.SpringSecurity.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUserName(String username);
    User findByEmail(String email);

   // Optional<User> findByUserName(String username);
}
