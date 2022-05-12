package com.man.springboot.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.man.springboot.security.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
 User findByUserName(String userName);
}
