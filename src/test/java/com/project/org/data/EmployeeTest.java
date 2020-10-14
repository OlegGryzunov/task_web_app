package com.project.org.data;

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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeService employeeService;
	
	private int tasks_init;
	private int employees_init;
	private int users_init;
	
	private int tasks_end;
	private int employees_end;
	private int users_end;	
	
	@Before
	public void start() {
		tasks_init = taskService.getAll().size();
		employees_init = employeeService.getAll().size();
		users_init = userService.findAllUsers().size();
	}
	
	@After
	public void finish() {
		
		tasks_end = taskService.getAll().size();
		employees_end = employeeService.getAll().size();
		users_end = userService.findAllUsers().size();
		
		Assert.assertEquals(tasks_init, tasks_end);
		Assert.assertEquals(employees_init,  employees_end);
		Assert.assertEquals(users_init, users_end);
	}
	
	@Test
	public void firstTest() {
		int employeesStart = employeeService.getAll().size();
		
		Task task0 = new Task();
		task0.setName("employeetest1");
		taskService.addTask(task0);
		
		User user0 = new User();
		user0.setUsername("employeetest1");
		user0.setPassword("password");
		userService.saveUser(user0);
		
		Task task = taskService.getById(task0.getId());		
		User user = userService.findUserById(user0.getId());
		
		Employee employee0 = new Employee(task.getId(), user);
		employeeService.save(employee0);
		
		Employee employee = employeeService.getById(employee0.getId());
		
		task.setPerformer(employee);
		taskService.editTask(task);
		
		Task task1 = taskService.getById(task.getId());
		
		Assert.assertEquals(task1.getPerformer().getUser().getId(), user.getId());
		Assert.assertEquals(task1.getId(), task1.getPerformer().getTaskId());
		Assert.assertNotNull(employee.getId());
		Assert.assertEquals(employee.getUser().getId(), user.getId());
		Assert.assertEquals(employee.getTaskId(), task1.getId());
		
		User user2 = new User();
		user2.setUsername("username_employeetest1");
		user2.setPassword("password2");
		
		userService.saveUser(user2);
		
		Employee employee1 = employeeService.getById(employee.getId());
		Assert.assertEquals(employee.getTaskId(), employee1.getTaskId());
		Assert.assertEquals(employee.getUser().getId(), employee1.getUser().getId());
		
		employee1.setUser(user2);
		employeeService.save(employee1);
		
		Task task2 = taskService.getById(task1.getId());
		
		Assert.assertEquals(task2.getPerformer().getUser().getId(), user2.getId());	
		Assert.assertTrue(userService.deleteUser(user2));
		
		User user3 = userService.findUserById(user2.getId());
		Assert.assertNull(user3.getId());
		
		int employeesFinish = employeeService.getAll().size();
		Assert.assertEquals(employeesStart, employeesFinish);
		
		taskService.delete(taskService.getById(task0.getId()));
		userService.deleteUser(userService.findUserById(user0.getId()));
	}
	
	@Test
	public void secondTest() {
		int employeesStart = employeeService.getAll().size();
		
		Task task0 = new Task();
		task0.setName("employeetest2");
		taskService.addTask(task0);
		
		User user0 = new User();
		user0.setUsername("employeetest2");
		user0.setPassword("password");
		userService.saveUser(user0);
		
		Task task = taskService.getById(task0.getId());
		User user = userService.findUserById(user0.getId());
		
		Employee employee = new Employee(task.getId(), user);
		employeeService.save(employee);
		
		Employee employee2 = employeeService.getById(employee.getId());
				
		task.setPerformer(employee2);
		taskService.editTask(task);
		
		Task task1 = taskService.getById(task.getId());
		
		Assert.assertEquals(task1.getPerformer().getUser().getId(), user.getId());
		Assert.assertEquals(task1.getId(), task1.getPerformer().getTaskId());
		Assert.assertNotNull(employee2.getId());
		
		Task task2 = new Task();
		task2.setName("taskname_employeetest2");
		
		taskService.addTask(task2);
		
		Employee employee1 = employeeService.getById(employee2.getId());
		Assert.assertEquals(employee2.getTaskId(), employee1.getTaskId());
		Assert.assertEquals(employee2.getUser().getId(), employee1.getUser().getId());
		
		Task task3 = taskService.getById(task2.getId());
		
		employee1.setTaskId(task3.getId());
		employeeService.save(employee1);
		
		Employee employee3 = employeeService.getById(employee1.getId());
		Task task5 = taskService.getById(task1.getId());
		
		Assert.assertEquals(task5.getPerformer().getTaskId(), task3.getId());
		Assert.assertNull(task3.getPerformer());
		Assert.assertEquals(task3.getId(), employee3.getTaskId());
		
		taskService.delete(task3);
		
		Task task4 = taskService.getById(task3.getId());
		Assert.assertNull(task4);
		
		int employeesFinish = employeeService.getAll().size();
		Assert.assertEquals(employeesStart, employeesFinish);
		
		taskService.delete(taskService.getById(task0.getId()));
		userService.deleteUser(userService.findUserById(user0.getId()));
	}
	
	@Test
	public void thirdTest() {
		int employeesStart = employeeService.getAll().size();
		
		Task task0 = new Task();
		task0.setName("employeetest3");
		taskService.addTask(task0);
		
		User user0 = new User();
		user0.setUsername("employeetest3");
		user0.setPassword("password");
		userService.saveUser(user0);
		
		Task task = taskService.getById(task0.getId());
		User user = userService.findUserById(user0.getId());
		
		User user1 = new User();
		user1.setUsername("username_employeetest3");
		user1.setPassword("password2");
		
		userService.saveUser(user1);
		
		User user2 = userService.findUserById(user1.getId());
		
		Employee employee = new Employee(task.getId(), user2);
		employeeService.save(employee);
		
		Employee employee1 = employeeService.getById(employee.getId());
		
		task.setPerformer(employee1);
		taskService.editTask(task);
		
		Task task1 = taskService.getById(task.getId());
		
		Assert.assertEquals(task1.getPerformer().getUser().getId(), user2.getId());
		Assert.assertEquals(task1.getId(), task1.getPerformer().getTaskId());
		Assert.assertNotNull(employee1.getId());
		Assert.assertEquals(employee1.getTaskId(), task1.getId());
		Assert.assertEquals(employee1.getUser().getId(), user2.getId());
		
		employee1.setUser(user);
		employeeService.save(employee1);
		
		Employee employee2 = employeeService.getById(employee1.getId());
		
		Task task2 = taskService.getById(task1.getId());
		
		Assert.assertEquals(task2.getPerformer().getUser().getId(), user.getId());		
		Assert.assertEquals(employee2.getUser().getId(), user.getId());
		
		Assert.assertTrue(userService.deleteUser(user2));
		User user3 = userService.findUserById(user2.getId());
		Assert.assertNull(user3.getId());
		
		int employeesFinish = employeeService.getAll().size();
		Assert.assertEquals(employeesStart + 1, employeesFinish);
		
		employeeService.delete(employee2);
		
		taskService.delete(taskService.getById(task0.getId()));
		userService.deleteUser(userService.findUserById(user0.getId()));
	}
	
	@Test
	public void fourthTest() {
		int employeesStart = employeeService.getAll().size();
		
		Task task0 = new Task();
		task0.setName("employeetest4");
		taskService.addTask(task0);
		
		User user0 = new User();
		user0.setUsername("employeetest4");
		user0.setPassword("password");
		userService.saveUser(user0);
		
		Task task = taskService.getById(task0.getId());
		User user = userService.findUserById(user0.getId());
		
		Task task2 = new Task();
		task2.setName("taskname_employeetest4");
		
		taskService.addTask(task2);
		
		Task task3 = taskService.getById(task2.getId());
		
		Employee employee = new Employee(task3.getId(), user);
		employeeService.save(employee);
		
		Employee employee2 = employeeService.getById(employee.getId());
		
		task3.setPerformer(employee2);
		taskService.editTask(task3);
		
		Task task4 = taskService.getById(task3.getId());
		
		Assert.assertEquals(task4.getPerformer().getUser().getId(), user.getId());
		Assert.assertEquals(task4.getPerformer().getTaskId(), task4.getId());
		Assert.assertNotNull(employee2.getId());
		Assert.assertEquals(employee2.getTaskId(), task4.getId());
		Assert.assertEquals(employee2.getUser().getId(), user.getId());
						
		employee2.setTaskId(task.getId());
		employeeService.save(employee2);
		
		Employee employee1 = employeeService.getById(employee2.getId());
		
		Task task5 = taskService.getById(task4.getId());
		
		Assert.assertEquals(task5.getPerformer().getTaskId(), task.getId());
		Assert.assertNotEquals(employee1.getTaskId(), task5.getId());
		Assert.assertNull(task.getPerformer());
		
		taskService.delete(task5);
		
		Task task6 = taskService.getById(task5.getId());
		Assert.assertNull(task6);
		
		int employeesFinish = employeeService.getAll().size();
		Assert.assertEquals(employeesStart + 1, employeesFinish);
		
		employeeService.delete(employee1);
		
		taskService.delete(taskService.getById(task0.getId()));
		userService.deleteUser(userService.findUserById(user0.getId()));
	}
}