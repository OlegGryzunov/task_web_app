package com.project.org.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.org.data.entity.Task;
import com.project.org.data.other.Importance;
import com.project.org.data.other.Status;
import com.project.org.data.service.TaskService;

@Controller
public class EditController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("edit")
	public String editMenu(@RequestParam("selectedId") Long selectId, @RequestParam("name") String name, 
			@RequestParam("date") @DateTimeFormat(pattern = "MM/dd/yyyy hh:mm a") Calendar date, 
			@RequestParam("importance") Importance importance, @RequestParam("status") Status status) {
		Task task = taskService.getById(selectId);
		if (task != null) {
			task.setName(name);
			task.setDate(date);
			task.setImportance(importance);
			task.setStatus(status);
			taskService.editTask(task);
		}
		return "redirect:menu";
	}
}
