package com.project.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.org.data.entity.Description;
import com.project.org.data.entity.Task;
import com.project.org.data.service.TaskService;

@Controller
public class ResultController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("result")
	public String resultMenu(@RequestParam("selectedId") Long selectId, @RequestParam("result") String result) {
		Task task = taskService.getById(selectId);
		if (task != null) {
			Description description;
			if (task.getDescription()==null)
				description = new Description();
			else
				description = task.getDescription();
			description.setResult(result);
			task.setDescription(description);
			taskService.editTask(task);
		}
		return "redirect:menu";
	}
}
