package com.cspinformatique.eve.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
@RequestMapping("/")
public class MainController {
	@Autowired private UserController userController;
	
	@RequestMapping
	public String getMainPage(Model model){
		return "user/login";
	}
	
	@RequestMapping(value="/login", params="login_error")
	public String displayLoginError(Model model){
		model.addAttribute("error", "Invalid Username / Password.");
		
		return "user/login";
	}
}
