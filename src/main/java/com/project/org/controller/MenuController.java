package com.project.org.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.org.data.entity.Description;
import com.project.org.data.entity.Task;
import com.project.org.data.entity.User;
import com.project.org.data.other.Importance;
import com.project.org.data.other.Status;
import com.project.org.data.service.TaskService;
import com.project.org.data.service.UserService;

@Controller
@RequestMapping("menu")
public class MenuController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	@GetMapping()
	public ModelAndView menu(HttpSession session) {
		ModelAndView MyModel = new ModelAndView("menu", HttpStatus.OK);
		
		List<Task> firstListTasks = taskService.getByParentId();		
		MyModel.addObject("firstTasksList", firstListTasks);
		MyModel.addObject("SimpleDateFormat", new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		MyModel.addObject("DateFormatForEdit", new SimpleDateFormat("MM/dd/yyyy hh:mm a"));
		
		Task selectedTask = taskService.getById((Long) session.getAttribute("MainSelect"));
		if (selectedTask == null & firstListTasks.size() != 0)
			selectedTask = firstListTasks.get(0);
		MyModel.addObject("selectedTask", selectedTask);
		
		List<Task> childrensListTasks = taskService.getByParentId(selectedTask != null ? selectedTask.getId() : 1);
		MyModel.addObject("childrensTasksList", childrensListTasks);
		
		List<User> users = userService.findUsersByRole("ROLE_USER");
		MyModel.addObject("usersList", users);
		
		return MyModel;
	}
	
	@PostMapping()
	public ModelAndView selectMenu(@RequestParam("t") Long select, HttpSession session) {
		ModelAndView MyModel = new ModelAndView("menu", HttpStatus.OK);
		
		List<Task> firstListTasks = this.taskService.getByParentId();		
		MyModel.addObject("firstTasksList", firstListTasks);
		MyModel.addObject("SimpleDateFormat", new SimpleDateFormat("dd.MM.yyyy HH:mm"));
		MyModel.addObject("DateFormatForEdit", new SimpleDateFormat("MM/dd/yyyy hh:mm a"));
		
		Task selectedTask = taskService.getById(select);
		if (selectedTask != null) {
			MyModel.addObject("selectedTask", selectedTask);
			session.setAttribute("MainSelect", select);
		}	
		
		List<Task> childrensListTasks = taskService.getByParentId(selectedTask.getId());
		MyModel.addObject("childrensTasksList", childrensListTasks);
		
		List<User> users = userService.findUsersByRole("ROLE_USER");
		ArrayList<String> userNames = new ArrayList<String>();
		for (User us : users)
			userNames.add(us.getUsername());
		MyModel.addObject("userNamesList", userNames);
		
		return MyModel;
	}
}