package com.ticket.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.platform.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByMail(String mail);

	public List<User> findByName(String name);

	public List<User> findByRolesName(String roleName);

}