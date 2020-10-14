package com.project.org.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.org.data.entity.Employee;
import com.project.org.data.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public void delete(Employee employee) {
		employeeRepository.delete(employee);
	}
	
	public Employee getById(Long id) {
		return id != null ? employeeRepository.findById(id).orElse(null) : null;
	}
}
