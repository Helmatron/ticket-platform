package com.ticket.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.platform.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByMail(String mail);

	List<User> findByName(String name);

	List<User> findByRolesName(String roleName);

}