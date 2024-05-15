package com.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.entity.Roles;
import com.webapp.entity.User;
import com.webapp.global.GlobalData;
import com.webapp.repo.RolesRepo;
import com.webapp.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RolesRepo rolesRepo;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public ModelAndView postRegister(@ModelAttribute User user, HttpServletRequest request){
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		List<Roles> roles = new ArrayList<>();
		roles.add(rolesRepo.findById(2).get());
		user.setRoles(roles);
		userRepo.save(user);
		return new ModelAndView("redirect:/login");
	}
	
	
}
