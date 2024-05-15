package com.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webapp.entity.CustomUserDetails;
import com.webapp.entity.User;
import com.webapp.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByEmail(email);
		user.orElseThrow(()-> new UsernameNotFoundException("User not found..."));
		return user.map(CustomUserDetails::new).get();
	}

}
