package com.ticket.platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain filterDashboard(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests()
	        .requestMatchers("/admin/**").hasAuthority("ADMIN")
	        .requestMatchers("/operator/lista_operatori").hasAuthority("ADMIN")
	        .requestMatchers("/operator/index").hasAuthority("OPERATOR")
	        .requestMatchers("/operator/dettagli_operatore").authenticated()
	        .requestMatchers("/note/**").authenticated()
	        .requestMatchers("/ticket/nuovo_ticket").hasAnyAuthority("ADMIN")
	        .requestMatchers("/ticket/dettagli_ticket").authenticated()
	        .requestMatchers("/ticket/edit_ticket").authenticated()
	        .requestMatchers("/resources/**", "/css/**", "/js/**", "/login").permitAll()
	        .anyRequest().authenticated()
	        .and().formLogin().permitAll().defaultSuccessUrl("/default", true)
	        .and().logout().permitAll();

	    return http.build();
	}
	
	@Bean
	 DatabaseUserDetailsService userDetailsService() {
	 return new DatabaseUserDetailsService();
	 }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider autentiAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
}
