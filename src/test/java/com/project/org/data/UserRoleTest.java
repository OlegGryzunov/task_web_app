package com.project.org.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.project.org.data.entity.User;
import com.project.org.data.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	public void createUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		
		Assert.assertEquals(user.getUsername(), "username");
		Assert.assertEquals(user.getPassword(), "password");
	}
	
	@Test
	public void firstTest() {
		int users1 = userService.findAllUsers().size();
		
		User user = new User();
		user.setUsername("username");
		user.setPassword(bCryptPasswordEncoder.encode("password"));
		userService.saveUser(user);
		
		UserDetails user1 = userService.loadUserByUsername("username");
		User user2 = userService.findUserById(user.getId());
		
		Assert.assertEquals(user1.getUsername(), user2.getUsername());
		Assert.assertEquals(user1.getPassword(), user2.getPassword());
		
		user2.setUsername("username2");
		user2.setPassword(bCryptPasswordEncoder.encode("password2"));
		userService.saveUser(user2);
		
		Long id = user2.getId();
		User user3 = userService.findUserById(id);
		
		Assert.assertEquals("username2", user3.getUsername());
		Assert.assertEquals(user2.getUsername(), user3.getUsername());
		Assert.assertEquals(user2.getPassword(), user3.getPassword());
		
		userService.deleteUser(user2);
		
		User user4 = userService.findUserById(id);
		Assert.assertNull(user4.getUsername());
		
		int users2 = userService.findAllUsers().size();
		Assert.assertEquals(users1, users2);
	}
}
