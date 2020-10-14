package com.project.org.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.org.data.entity.Description;
import com.project.org.data.entity.Task;
import com.project.org.data.repository.DescriptionRepository;
import com.project.org.data.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private DescriptionRepository descriptionRepository;
	
	public List<Task> getAll() {
		return taskRepository.findAll();
	}
	
	public List<Description> getAllDescriptions() {
		return descriptionRepository.findAll();
	}
	
	public Task getById(Long id) {
		return id != null ? taskRepository.findById(id).orElse(null) : null;
	}
	
	public List<Task> getByParentId(Long id) {
		return taskRepository.findWhereParentId(id);
	}
	
	public List<Task> getByParentId() {
		return taskRepository.findWhereParentId();
	}
	
	public Task addTask(Task task) {
		return taskRepository.saveAndFlush(task);
	}
	
	public Task editTask(Task task) {
		return taskRepository.saveAndFlush(task);
	}
	
	public void delete(Task task) {
		taskRepository.delete(task);
	}
}
