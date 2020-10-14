package com.project.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.org.data.entity.Task;
import com.project.org.data.service.TaskService;

@Controller
public class DeleteController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("delete")
	public String deleteMenu(@RequestParam("deleteId") Long deleteId) {
		Task delTask = taskService.getById(deleteId);
		taskService.delete(delTask);
		return "redirect:menu";
	}
}
