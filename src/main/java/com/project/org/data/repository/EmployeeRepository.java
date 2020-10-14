package com.project.org.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.org.data.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("select u from Employee u left join fetch u.user where u.id = ?1")
	public Optional<Employee> findById(Long id);
}
