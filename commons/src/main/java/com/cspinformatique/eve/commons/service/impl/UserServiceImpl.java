package com.cspinformatique.eve.commons.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cspinformatique.eve.commons.exception.UserAlreadyExistsException;
import com.cspinformatique.eve.commons.exception.UserNotFoundException;
import com.cspinformatique.eve.commons.model.AccessMask;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.User;
import com.cspinformatique.eve.commons.repository.eve.UserRepository;
import com.cspinformatique.eve.commons.service.KeyService;
import com.cspinformatique.eve.commons.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Resource
	private UserRepository userRepository;
	
	@Autowired private KeyService keyService;

	@Override
	public User authenticateUser(String username, String password) {
		User user = this.userRepository.findByUsername(username);
		
		if(user == null || !user.getPassword().equals(password)){
			throw new UserNotFoundException();
		}
		
		return user;
	}


	
	@Override 
	public User getUser(int id){
		return this.userRepository.findOne(id);
	}
	
	@Override
	public User getUser(String username){
		return this.userRepository.findByUsername(username);
	}
	
	@Override
	public boolean hasAccessTo(String username, AccessMask accessMask){
		for(Key key : this.keyService.getUserKeys(this.getUser(username).getId())){
			if(this.keyService.hasAccessTo(key, accessMask)){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void registerUser(User user) {		
		if(this.getUser(user.getUsername()) != null){
			throw new UserAlreadyExistsException();
		}
		
		this.userRepository.saveAndFlush(user);
	}
}
