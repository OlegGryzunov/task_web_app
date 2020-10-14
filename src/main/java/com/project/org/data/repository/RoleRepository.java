package com.project.org.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.org.data.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query("select u from Role u where u.role_name = ?1")
	Role findByRoleName(String role_name);
}
