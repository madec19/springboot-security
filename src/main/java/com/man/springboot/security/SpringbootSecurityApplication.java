package com.man.springboot.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.man.springboot.security.domain.Role;
import com.man.springboot.security.domain.User;
import com.man.springboot.security.service.UserService;

@SpringBootApplication
public class SpringbootSecurityApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		userService.saveRole(new Role(null, "ADMIN_ROLE"));
		userService.saveRole(new Role(null, "USER_ROLE"));
		
		userService.saveUser(new User(null, "Manoj", "madec19", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Utarsh Tripathi", "ut", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Animesh Sinha", "sinha", "1234", new ArrayList<>()));
		
		userService.addRoleToUser("madec19", "ADMIN_ROLE");
		userService.addRoleToUser("madec19", "USER_ROLE");
		userService.addRoleToUser("ut", "ADMIN_ROLE");
		userService.addRoleToUser("sinha", "USER_ROLE");
		
	}

}
