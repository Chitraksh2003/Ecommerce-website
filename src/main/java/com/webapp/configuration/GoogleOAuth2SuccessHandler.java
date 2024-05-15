package com.webapp.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.webapp.entity.Roles;
import com.webapp.entity.User;
import com.webapp.repo.RolesRepo;
import com.webapp.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	RolesRepo rolesRepo;
	
	@Autowired
	UserRepo userRepo;

	private RedirectStrategy redirectStrategy= new DefaultRedirectStrategy();
	
//	HttpServletRequest httpServletRequest;
//	HttpServletResponse httpServletResponse;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		if(userRepo.findUserByEmail(email).isPresent()) {
			
		}else {
			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			if(token.getPrincipal().getAttributes().get("family_name")==null) {
			}else {
				user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());	
			}
			user.setEmail(email);
			List<Roles> role = new ArrayList<>();
			role.add(rolesRepo.findById(1).get());
			user.setRoles(role);
			userRepo.save(user);
		}
		redirectStrategy.sendRedirect(request, response, "/");
	}
	
	
	
}
