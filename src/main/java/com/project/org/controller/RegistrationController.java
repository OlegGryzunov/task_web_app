package com.project.org.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.org.data.entity.User;
import com.project.org.data.service.UserService;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult, Model model) {
		
		 if (bindingResult.hasErrors()) {
			 model.addAttribute("registrationError", "Логин и пароль должен состоять из более трех символов");
	         return "registration";
	     }
	     if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
	         model.addAttribute("registrationError", "Пароли не совпадают");
	         return "registration";
	     }
	     if (!userService.saveUser(userForm)) {
	         model.addAttribute("registrationError", "Пользователь с таким именем уже существует");
	         return "registration";
	     }
	     
	     return "redirect:/"; 
	}
}