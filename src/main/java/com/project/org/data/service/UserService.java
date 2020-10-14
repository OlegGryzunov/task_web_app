package com.project.org.data.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.org.data.entity.Role;
import com.project.org.data.entity.User;
import com.project.org.data.repository.RoleRepository;
import com.project.org.data.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
	}
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public List<User> findUsersByRole(String rolename) {
		Role role = roleRepository.findByRoleName(rolename);
		if (role == null)
			return null;
		return userRepository.findAllByRoles(role);
	}
	
	public User findUserById(Long userId) {
		Optional<User> userFromDb = userRepository.findById(userId);
		return userFromDb.orElse(new User());
	}
	
	public boolean saveUser(User user) {
		User userFromDB = userRepository.findByUsername(user.getUsername());
		
		if (userFromDB != null) {
            return false;
        }
		
		user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}
	
	public boolean deleteUser(User user) {
		 if (userRepository.findById(user.getId()).isPresent()) {
			 userRepository.deleteById(user.getId());
			 return true;
		 }
		 return false;
	}
}
