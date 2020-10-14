package com.project.org.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.org.data.entity.Task;
import com.project.org.data.entity.User;
import com.project.org.data.service.TaskService;

@Controller
public class AddController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("add")
	public String addMenu(@ModelAttribute("task") Task task, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Task taskResult = taskService.addTask(task);
			if (taskResult!=null)
				session.setAttribute("MainSelect", taskResult.getId());		
		}		
		return "redirect:menu";
	}	
}
