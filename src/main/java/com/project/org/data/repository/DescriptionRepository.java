package com.project.org.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.org.data.entity.Description;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

}
