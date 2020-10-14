package com.project.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.org.data.entity.Description;
import com.project.org.data.entity.Task;
import com.project.org.data.service.TaskService;

@Controller
public class TargetController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("target")
	public String targetMenu(@RequestParam("selectedId") Long selectId, @RequestParam("target") String target) {
		Task task = taskService.getById(selectId);
		if (task != null) {
			Description description;
			if (task.getDescription()==null)
				description = new Description();
			else
				description = task.getDescription();
			description.setTarget(target);
			task.setDescription(description);
			taskService.editTask(task);
		}
		return "redirect:menu";
	}	
}
