package com.project.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TasksWebAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TasksWebAppApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void testJpaMethods() {
	}

}
