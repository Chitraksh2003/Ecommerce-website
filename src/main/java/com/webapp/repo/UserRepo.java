package com.webapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findUserByEmail(String email);
}
