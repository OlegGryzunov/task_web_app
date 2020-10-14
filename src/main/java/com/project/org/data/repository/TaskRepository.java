package com.project.org.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.org.data.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("select u from Task u left join fetch u.watchers left join fetch u.performer a left join fetch u.supervisor b left join fetch a.user left join fetch b.user where u.id = ?1")
	public Optional<Task> findById(Long id);
	
	@Query("select u from Task u where u.parentId = ?1 order by u.id desc")
	public List<Task> findWhereParentId(Long parentId);
	
	@Query("select u from Task u where u.parentId = null order by u.id desc")
	public List<Task> findWhereParentId();
}
