package com.guoys.platform.webcomponents.entity.DO;


import com.guoys.platform.persistence.annotation.NameStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;


@Table(name = "platform_user")
@NameStyle
public class User {

    @Id
	private String id;

	private String username;

	private String password;

	private boolean admin = false;

	@Transient
	private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public boolean isAdmin() {
        return "admin".equals(this.username);
    }

}