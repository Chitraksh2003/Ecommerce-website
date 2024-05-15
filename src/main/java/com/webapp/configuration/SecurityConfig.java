package com.webapp.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.webapp.service.CustomUserDetailService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		
		
		http	
			.authorizeHttpRequests()
			.requestMatchers("/admin/**").hasRole("ADMIN")	
			.requestMatchers("/home/** ","/","/shop/**","/register").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.failureUrl("/login?error= true")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.passwordParameter("password")
			.and()
			.oauth2Login()
			.loginPage("/login")
			.successHandler(googleOAuth2SuccessHandler)
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.deleteCookies("JSESSIONID")
			.and()
			.exceptionHandling()
			.and().csrf()
			.disable();
		
			http.authenticationProvider(authenticationProvider());
			return http.build();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManger(AuthenticationConfiguration authenticationConfig)throws Exception {
		return authenticationConfig.getAuthenticationManager();
	}
	
//	@Bean
//	IgnoredRequestConfigurer webSecurity() {
//		return security.ignoring()
//    .requestMatchers("/resources/**", "/static/**", "/images/**", "/ProductImages/**", "/css/**", "/js/**");
//	}


//    @Bean
//    public SecurityFilterChain ignoreStaticRequests(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .requestMatchers("/resources/**", "/static/**", "/images/**", "/ProductImages/**", "/css/**", "/js/**").permitAll();
//        return http.build();
//    }
}
