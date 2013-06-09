package com.cspinformatique.eve.commons.service;

import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.User;

public interface UserService {
	public User authenticateUser(String username, String password);
		
	public User getUser(int id);
	
	public User getUser(String username);
	
	public boolean hasAccessTo(String username, AccessMask accessMask);
	
	public void registerUser(User user);
}
