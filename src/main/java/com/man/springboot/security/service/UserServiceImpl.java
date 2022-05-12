package com.man.springboot.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.man.springboot.security.domain.Role;
import com.man.springboot.security.domain.User;
import com.man.springboot.security.repo.RoleRepo;
import com.man.springboot.security.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		//user.setPassword(passwordEncoder.encode(user.getName()));
		return userRepo.save(user);

	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {
		User user = userRepo.findByUserName(userName);
		Role role = roleRepo.findByName(roleName);

		user.getRoles().add(role);

	}

	@Override
	public User getUser(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepo.findByUserName(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");

		}
		log.info("User loaded by spring " + user.toString());
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getName(),  new BCryptPasswordEncoder().encode(user.getPassword()), authorities);

	}

}
