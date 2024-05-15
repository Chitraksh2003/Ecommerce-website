package com.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.entity.Roles;

public interface RolesRepo extends JpaRepository<Roles, Integer> {

}
