package com.ticket.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.platform.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByMail(String mail);
		
}