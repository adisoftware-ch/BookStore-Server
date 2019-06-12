package ch.adisoftware.bookstore.persistence.model.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String password;
	
	private List<String> roles = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstName;
	}
	
	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastname() {
		return lastName;
	}
	
	public void setLastname(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public void setUsername(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getRoles() {
    	return roles;
    }
	
	public void setRoles(List<String> roles) {
    	this.roles = roles;
    }

}
