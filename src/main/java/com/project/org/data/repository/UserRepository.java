package com.project.org.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.org.data.entity.Role;
import com.project.org.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	List<User> findAllByRoles(Role role);
}
