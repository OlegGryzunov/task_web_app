package com.project.org.data.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.project.org.data.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "app_role")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
	
	@Id
	private Long id;
	
	@NonNull
	private String role_name;
	
	@ManyToMany(mappedBy = "roles", cascade = CascadeType.REFRESH)
    private Set<User> users;
	
	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String role_name) {
		this.id = id;
		this.role_name = role_name;
	}
	
	@Override
	public String getAuthority() {
		return getRole_name();
	}
}
