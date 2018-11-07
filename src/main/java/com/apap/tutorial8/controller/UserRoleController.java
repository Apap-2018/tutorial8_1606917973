package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{8,20})";
	
	
	public boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();    
	}
	
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		if(validatePassword(user.getPassword()) == false) {
			return "homeWithPassReq";
		}
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePassword(@RequestParam("username") String username, @RequestParam("newPassword") String newPassword) {
		if(validatePassword(newPassword) == false) {
			return "homeWithPassReq";
		}
		userService.updatePassword(username, newPassword);
		return "home";
	}
	
	
	
}
