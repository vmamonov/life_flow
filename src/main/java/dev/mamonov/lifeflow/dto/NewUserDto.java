package dev.mamonov.lifeflow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NewUserDto {
	@NotEmpty
	@Size(min = 2, max = 150, message = "Username must not be empty")
	private String username;
	
	@NotEmpty
	@Size(min = 8, message = "Password must be longer as 8 characters")
	private String password;

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
}
