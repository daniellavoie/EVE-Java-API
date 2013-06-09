package com.cspinformatique.eve.ui.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cspinformatique.eve.commons.exception.UserAlreadyExistsException;
import com.cspinformatique.eve.commons.exception.UserNotFoundException;
import com.cspinformatique.eve.commons.model.Key;
import com.cspinformatique.eve.commons.model.User;
import com.cspinformatique.eve.commons.service.CharacterService;
import com.cspinformatique.eve.commons.service.UserService;

@Controller
@Transactional
@RequestMapping("/user")
public class UserController {
	@Autowired private CharacterController characterController;
	@Autowired private KeyController keyController;
	
	@Autowired private CharacterService characterService;
	@Autowired private UserService userService;
	
	@RequestMapping
	public String displayLoginError(Model model, @RequestParam String login_error){
		model.addAttribute("error", login_error);
		
		return "user/login";
	}
	
	@RequestMapping(params="login")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String login(Model model, Principal principal){
		try{
			User user = this.userService.getUser(principal.getName());
			
			model.addAttribute("user", user);
			
			// If user has no key, redirect him to the key maintenance page.
			if(user.getKeys().size() == 0){
				// Redirect to Key Management page.
				return "redirect:/key";
			}else{
				return characterController.getDefaultCharacterAssets(model, principal);
			}
		}catch(UserNotFoundException userNotFoundEx){
			model.addAttribute("error", "Invalid username/password.");
			return "user/login";
		}
	}
	
	@RequestMapping(params="register", method=RequestMethod.GET)
	public String getRegistratrionPage(Model model){
		model.addAttribute("user", new User(0, "", "", new ArrayList<Key>(), "user"));
		
		return "user/register";
	}
	
	@RequestMapping(params="register", method=RequestMethod.POST)
	public String getRegistratrionPage(Model model, User user){
		try{
			this.userService.registerUser(user);
			
			return 
				"redirect:/static/j_spring_security_check" +
				"?j_username=" + user.getUsername() + 
				"&password=" + user.getPassword();
		}catch(UserAlreadyExistsException UserAlreadyExistsEx){
			model.addAttribute("error", "Username already exists.");
			
			return "user/register";
		}
	}
}
