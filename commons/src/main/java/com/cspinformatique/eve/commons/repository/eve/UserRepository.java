package com.cspinformatique.eve.commons.repository.eve;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cspinformatique.eve.commons.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
