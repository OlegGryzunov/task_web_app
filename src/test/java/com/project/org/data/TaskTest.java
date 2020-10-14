package com.project.org.data;

import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.project.org.data.service.TaskService;
import com.project.org.data.entity.Description;
import com.project.org.data.entity.Employee;
import com.project.org.data.entity.Task;
import com.project.org.data.entity.User;
import com.project.org.data.other.Importance;
import com.project.org.data.other.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {
	
	@Autowired
	private TaskService taskService;
	
	@Test
	public void plainNullObject() {
		Task task_1 = new Task();
		task_1.setName("task1");
		Task task_2 = taskService.addTask(task_1);
		Assert.assertNotNull(task_2);
		Assert.assertNotNull(task_2.getId());
		Assert.assertEquals(task_2.getName(), task_1.getName());
		Assert.assertEquals(task_2.getStatus(), Status.INPROGRESS);
		Assert.assertEquals(task_2.getImportance(), Importance.MID);
		Assert.assertEquals(task_2, task_1);
		Assert.assertNotNull(task_1.getId());
		Assert.assertNull(task_2.getDate());
		Assert.assertNull(task_2.getDescription());
		Assert.assertNull(task_2.getParentId());
		Assert.assertNull(task_2.getPerformer());
		Assert.assertNull(task_2.getSupervisor());
		Assert.assertNull(task_2.getWatchers());
		taskService.delete(task_2);
	}
	
	@Test
	public void secondTest() {
		int tasks1 = taskService.getAll().size();
		
		Task task1 = new Task();
		Task task2 = new Task();
		Task task3 = new Task();
		Task task4 = new Task();
		
		task1.setName("task2");
		task2.setName("task3");
		task3.setName("task4");
		task4.setName("task5");
		
		Importance importance1 = Importance.BOTTOM;
		Importance importance2 = Importance.HIGH;
		Importance importance3 = Importance.LOW;
		Importance importance4 = Importance.TOP;
		
		task1.setImportance(importance1);
		task2.setImportance(importance2);
		task3.setImportance(importance3);
		task4.setImportance(importance4);
		
		Status status1 = Status.COMPLETED;
		Status status2 = Status.COMPLETED;
		Status status3 = Status.FAILED;
		Status status4 = Status.FAILED;
		
		task1.setStatus(status1);
		task2.setStatus(status2);
		task3.setStatus(status3);
		task4.setStatus(status4);
		
		GregorianCalendar date1 = new GregorianCalendar(1978, 0, 31, 5, 32);
		GregorianCalendar date2 = new GregorianCalendar(2020, 11, 31, 18, 0);
		GregorianCalendar date3 = new GregorianCalendar(2027, 2, 28, 23, 59);
		GregorianCalendar date4 = new GregorianCalendar(2018, 7, 1, 15, 18);
		
		task1.setDate(date1);
		task2.setDate(date2);
		task3.setDate(date3);
		task4.setDate(date4);
		
		taskService.addTask(task1);
		taskService.addTask(task2);
		taskService.addTask(task3);
		taskService.addTask(task4);
		
		Task task1_bd = taskService.getById(task1.getId());
		Task task2_bd = taskService.getById(task2.getId());
		Task task3_bd = taskService.getById(task3.getId());
		Task task4_bd = taskService.getById(task4.getId());		
		
		Assert.assertEquals(task1_bd.getId(), task1.getId());
		Assert.assertEquals(task2_bd.getId(), task2.getId());
		Assert.assertEquals(task3_bd.getId(), task3.getId());
		Assert.assertEquals(task4_bd.getId(), task4.getId());
		
		Assert.assertEquals(task1_bd.getImportance(), importance1);
		Assert.assertEquals(task2_bd.getImportance(), importance2);
		Assert.assertEquals(task3_bd.getImportance(), importance3);
		Assert.assertEquals(task4_bd.getImportance(), importance4);
		
		Assert.assertEquals(task1_bd.getStatus(), status1);
		Assert.assertEquals(task2_bd.getStatus(), status2);
		Assert.assertEquals(task3_bd.getStatus(), status3);
		Assert.assertEquals(task4_bd.getStatus(), status4);
		
		Assert.assertEquals(task1_bd.getDate(), date1);
		Assert.assertEquals(task2_bd.getDate(), date2);
		Assert.assertEquals(task3_bd.getDate(), date3);
		Assert.assertEquals(task4_bd.getDate(), date4);
		
		task1.setName("TASK2_2");
		task2.setName("TASK3_3");
		task3.setName("TASK4_4");
		task4.setName("TASK5_5");
		
		Importance importance1_2 = Importance.MID;
		Importance importance2_2 = Importance.TOP;
		Importance importance3_2 = Importance.BOTTOM;
		Importance importance4_2 = Importance.LOW;
		
		task1.setImportance(importance1_2);
		task2.setImportance(importance2_2);
		task3.setImportance(importance3_2);
		task4.setImportance(importance4_2);
		
		Status status1_2 = Status.FAILED;
		Status status2_2 = Status.INPROGRESS;
		Status status3_2 = Status.COMPLETED;
		Status status4_2 = Status.INPROGRESS;
		
		task1.setStatus(status1_2);
		task2.setStatus(status2_2);
		task3.setStatus(status3_2);
		task4.setStatus(status4_2);
		
		GregorianCalendar date1_2 = new GregorianCalendar(1937, 11, 31, 0, 37);
		GregorianCalendar date2_2 = new GregorianCalendar(2017, 0, 31, 18, 0);
		GregorianCalendar date3_2 = new GregorianCalendar(2024, 10, 28, 23, 59);
		GregorianCalendar date4_2 = null;
		
		task1.setDate(date1_2);
		task2.setDate(date2_2);
		task3.setDate(date3_2);
		task4.setDate(date4_2);
		
		taskService.editTask(task1);
		taskService.editTask(task2);
		taskService.editTask(task3);
		taskService.editTask(task4);
		
		Task task1_bd2 = taskService.getById(task1.getId());
		Task task2_bd2 = taskService.getById(task2.getId());
		Task task3_bd2 = taskService.getById(task3.getId());
		Task task4_bd2 = taskService.getById(task4.getId());
		
		Assert.assertEquals(task1_bd2.getId(), task1.getId());
		Assert.assertEquals(task2_bd2.getId(), task2.getId());
		Assert.assertEquals(task3_bd2.getId(), task3.getId());
		Assert.assertEquals(task4_bd2.getId(), task4.getId());
		
		Assert.assertEquals(task1_bd2.getName(), "TASK2_2");
		Assert.assertEquals(task2_bd2.getName(), "TASK3_3");
		Assert.assertEquals(task3_bd2.getName(), "TASK4_4");
		Assert.assertEquals(task4_bd2.getName(), "TASK5_5");
		
		Assert.assertEquals(task1_bd2.getImportance(), importance1_2);
		Assert.assertEquals(task2_bd2.getImportance(), importance2_2);
		Assert.assertEquals(task3_bd2.getImportance(), importance3_2);
		Assert.assertEquals(task4_bd2.getImportance(), importance4_2);
		
		Assert.assertEquals(task1_bd2.getStatus(), status1_2);
		Assert.assertEquals(task2_bd2.getStatus(), status2_2);
		Assert.assertEquals(task3_bd2.getStatus(), status3_2);
		Assert.assertEquals(task4_bd2.getStatus(), status4_2);
		
		Assert.assertEquals(task1_bd2.getDate(), date1_2);
		Assert.assertEquals(task2_bd2.getDate(), date2_2);
		Assert.assertEquals(task3_bd2.getDate(), date3_2);
		Assert.assertEquals(task4_bd2.getDate(), date4_2);
		
		taskService.delete(task1);
		taskService.delete(task2);
		taskService.delete(task3);
		taskService.delete(task4);
		
		int tasks2 = taskService.getAll().size();
		Assert.assertEquals(tasks1, tasks2);
	}
	
	@Test
	public void thirdTest() {
		int tasks1 = taskService.getAll().size();
		int descriptions1 = taskService.getAllDescriptions().size();
		
		Task task = new Task();
		task.setName("task_1");
		
		Description description = new Description();
		description.setResult("result");
		description.setTarget("target");
		
		task.setDescription(description);
		
		taskService.addTask(task);
		
		Task task2 = taskService.getById(task.getId());
		
		Assert.assertEquals(task.getId(), task2.getId());
		Assert.assertEquals(task.getDescription().getId(), task2.getDescription().getId());
		Assert.assertEquals(task2.getDescription().getTarget(), "target");
		Assert.assertEquals(task2.getDescription().getResult(), "result");
		
		Description description2 = task2.getDescription();
		description2.setTarget("TARGET2");
		description2.setResult("RESULT2");
		task2.setDescription(description2);
		taskService.editTask(task2);
		
		Task task3 = taskService.getById(task2.getId());
		
		Assert.assertEquals(task2.getId(), task3.getId());
		Assert.assertEquals(task2.getDescription().getId(), task3.getDescription().getId());
		Assert.assertEquals(task3.getDescription().getTarget(), "TARGET2");
		Assert.assertEquals(task3.getDescription().getResult(), "RESULT2");
		
		Description description3 = task3.getDescription();
		description3.setTarget(null);
		description3.setResult(null);
		task3.setDescription(description3);
		taskService.editTask(task3);
		
		Task task4 = taskService.getById(task3.getId());
		
		Assert.assertEquals(task3.getId(), task4.getId());
		Assert.assertEquals(task3.getDescription().getId(), task4.getDescription().getId());
		Assert.assertNull(task3.getDescription().getTarget());
		Assert.assertNull(task3.getDescription().getResult());
		
		task4.getDescription().setTarget("abc123");
		task4.getDescription().setResult("xyz890");
		
		taskService.editTask(task4);
		
		Task task5 = taskService.getById(task4.getId());
		
		Assert.assertEquals(task5.getDescription().getTarget(), "abc123");
		Assert.assertEquals(task5.getDescription().getResult(), "xyz890");		
		
		task5.setDescription(null);
		taskService.editTask(task5);
		
		Task task6 = taskService.getById(task5.getId());
		
		Assert.assertNull(task6.getDescription().getTarget());
		Assert.assertNull(task6.getDescription().getResult());
		
		taskService.delete(task6);
		
		int tasks2 = taskService.getAll().size();
		Assert.assertEquals(tasks1, tasks2);
		
		int descriptions2 = taskService.getAllDescriptions().size();
		Assert.assertEquals(descriptions1, descriptions2);
	}
	
	@Test
	public void fourthTest() {
		int tasks1 = taskService.getAll().size();
		int descriptions1 = taskService.getAllDescriptions().size();
		
		Task task1 = new Task();
		Task task2 = new Task();
		Task task3 = new Task();
		Task task4 = new Task();
		
		task1.setName("task1_4");
		task2.setName("task2_4");
		task3.setName("task3_4");
		task4.setName("task4_4");
		
		Description description1 = new Description();
		Description description2 = new Description();
		Description description3 = new Description();
		Description description4 = new Description();
		
		description1.setTarget("target_one");
		description2.setTarget("target_two");
		description3.setTarget("target_three");
		description4.setTarget("target_four");
		
		description1.setResult("result_one");
		description2.setResult("result_two");
		description3.setResult("result_three");
		description4.setResult("result_four");
		
		task1.setDescription(description1);
		task2.setDescription(description2);
		task3.setDescription(description3);
		task4.setDescription(description4);
		
		taskService.addTask(task1);
		taskService.addTask(task2);
		taskService.addTask(task3);
		taskService.addTask(task4);
		
		task2.setParentId(task1.getId());
		task3.setParentId(task2.getId());
		task4.setParentId(task1.getId());
		
		taskService.editTask(task2);
		taskService.editTask(task3);
		taskService.editTask(task4);
		
		Assert.assertNull(task1.getParentId());
		Assert.assertEquals(task1.getId(), task2.getParentId());
		Assert.assertEquals(task2.getId(), task3.getParentId());
		Assert.assertEquals(task1.getId(), task4.getParentId());
		
		task1.setParentId(task2.getId());
		task2.setParentId(null);
		task3.setParentId(task1.getId());
		task4.setParentId(task2.getId());
		
		taskService.editTask(task1);
		taskService.editTask(task2);
		taskService.editTask(task3);
		taskService.editTask(task4);
		
		Assert.assertEquals(task2.getId(), task1.getParentId());
		Assert.assertNull(task2.getParentId());
		Assert.assertEquals(task1.getId(), task3.getParentId());
		Assert.assertEquals(task2.getId(), task4.getParentId());
		
		taskService.delete(task2);
		
		int tasks2 = taskService.getAll().size();
		Assert.assertEquals(tasks1, tasks2);
		
		int descriptions2 = taskService.getAllDescriptions().size();
		Assert.assertEquals(descriptions1, descriptions2);
	}
	
	@Test
	public void fifthTest() {
		User user1 = new User();
		user1.setUsername("Denchik");
		Employee employee1 = new Employee(1L, user1);
		Employee employee2 = employee1;
		User user2 = new User();
		user2.setUsername("Krolya");
		employee1.setUser(user2);
		Assert.assertEquals(employee1, employee2);
		Assert.assertEquals(employee2.getUser(), user2);
	}
}