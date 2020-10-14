package com.project.org.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.project.org.data.entity.Employee;
import com.project.org.data.entity.Task;
import com.project.org.data.entity.User;
import com.project.org.data.service.EmployeeService;
import com.project.org.data.service.TaskService;
import com.project.org.data.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformerSupervisorTest {
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeService employeeService;
	
	private int tasksStart;
	private int employeesStart;
	private int usersStart;
	
	private int tasksEnd;
	private int employeesEnd;
	private int usersEnd;
	
	@Before
	public void start() {
		tasksStart = taskService.getAll().size();
		employeesStart = employeeService.getAll().size();
		usersStart = userService.findAllUsers().size();
	}
	
	@After
	public void finish() {				
		tasksEnd = taskService.getAll().size();
		employeesEnd = employeeService.getAll().size();
		usersEnd = userService.findAllUsers().size();
		
		Assert.assertEquals(tasksStart, tasksEnd);
		Assert.assertEquals(employeesStart, employeesEnd);
		Assert.assertEquals(usersStart, usersEnd);
	}
	
	@Test
	public void firstTest() {		
		Task task1 = taskService.getById(createTask("PerSupTest1"));
		Assert.assertNotNull(task1);
		
		task1.setPerformer(null);
		task1.setSupervisor(null);
		taskService.editTask(task1);
		
		Task task2 = taskService.getById(task1.getId());
		Assert.assertNull(task2.getPerformer());
		Assert.assertNull(task2.getSupervisor());
		
		taskService.delete(task2);
	}
	
	@Test
	public void setPerformerMyId() {		
		Task task1 = taskService.getById(createTask("PerSupTest2"));
		Assert.assertNotNull(task1);
		
		User user1 = userService.findUserById(createUser("PerSupTest2"));
		Assert.assertNotNull(user1);
		
		Employee employee1 = employeeService.getById(createEmployee(task1.getId(), user1));
		Assert.assertNotNull(employee1);
		
		task1.setPerformer(employee1);
		taskService.editTask(task1);
		
		Task task2 = taskService.getById(task1.getId());
		
		Assert.assertEquals(task2.getPerformer().getId(), employee1.getId());
		Assert.assertEquals(task2.getId(), task2.getPerformer().getTaskId());
		Assert.assertEquals(user1.getId(), task2.getPerformer().getUser().getId());
		Assert.assertNull(task2.getSupervisor());
		Assert.assertEquals(task2.getWatchers().size(), 1);
		
		taskService.delete(taskService.getById(task2.getId()));
		userService.deleteUser(userService.findUserById(user1.getId()));		
	}
	
	@Test
	public void createPerformerNotMyId() {
		Task firstTask1 = taskService.getById(createTask("PerSupTest3_1"));
		Assert.assertNotNull(firstTask1);
		
		User user1 = userService.findUserById(createUser("PerSupTest3"));
		Assert.assertNotNull(user1);
		
		Long employeeId = createEmployee(firstTask1.getId(), user1);
		Employee employee1 = employeeService.getById(employeeId);
		Assert.assertNotNull(employee1);
		
		Task secondTask1 = taskService.getById(createTask("PerSupTest3_2"));
		Assert.assertNotNull(secondTask1);
		
		secondTask1.setPerformer(employee1);
		taskService.editTask(secondTask1);
		
		Task firstTask2 = taskService.getById(firstTask1.getId());
		Task secondTask2 = taskService.getById(secondTask1.getId());
		
		Assert.assertEquals(user1.getId(), secondTask2.getPerformer().getUser().getId());
		Assert.assertEquals(firstTask2.getId(), secondTask2.getPerformer().getTaskId());
		Assert.assertNull(firstTask2.getPerformer());
		Assert.assertEquals(secondTask2.getPerformer().getId(), employee1.getId());
		Assert.assertEquals(firstTask2.getWatchers().size(), 0);
		Assert.assertEquals(secondTask2.getWatchers().size(), 1);
		
		taskService.delete(secondTask2);
		
		Employee employee2 = employeeService.getById(employeeId);
		Assert.assertNotNull(employee2.getId());
		
		taskService.delete(taskService.getById(firstTask2.getId()));
		userService.deleteUser(userService.findUserById(user1.getId()));
	}
	
	public Long createTask(String name) {
		Task task = new Task();
		task.setName(name);
		taskService.addTask(task);
		return task.getId();
	}
	
	public Long createUser(String name) {
		User user = new User();
		user.setUsername(name);
		user.setPassword("password");
		userService.saveUser(user);
		return user.getId();
	}
	
	public Long createEmployee(Long taskId, User user) {
		Employee employee = new Employee(taskId, user);
		employeeService.save(employee);
		return employee.getId();
	}
}
