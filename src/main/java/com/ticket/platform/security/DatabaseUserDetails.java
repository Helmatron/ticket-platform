package com.ticket.platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import java.lang.Override;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ticket.platform.model.Role;
import com.ticket.platform.model.User;

public class DatabaseUserDetails implements UserDetails{

	private final Long id;
	private final String mail;
	private final String password;
	private final String name;
	private final String surname;
	private final Set<GrantedAuthority> authorities;
	
	
	
	public DatabaseUserDetails(User user) {
		this.id = user.getId();
		this.mail = user.getMail();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.authorities = new HashSet<>();
		for(Role ruolo : user.getRoles()) {
			this.authorities.add(new SimpleGrantedAuthority(ruolo.getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.mail;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
	
}
