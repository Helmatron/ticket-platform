package com.ticket.platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ticket.platform.model.User;
import com.ticket.platform.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByMail(mail);
		
		if(user.isPresent()) {
			return new DatabaseUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("Indirizzo e-mail non trovato");
		}

	}

}