package com.man.springboot.security.service;

import java.util.List;

import com.man.springboot.security.domain.Role;
import com.man.springboot.security.domain.User;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String userName, String roleName);

	User getUser(String userName);

	List<User> getUsers();

}
