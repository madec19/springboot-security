package com.man.springboot.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.man.springboot.security.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
