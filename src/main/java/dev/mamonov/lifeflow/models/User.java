package dev.mamonov.lifeflow.models;

import dev.mamonov.lifeflow.security.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * @todo checking to unique username
	 */
	@NotEmpty
	@Column(name="username")
	@Size(min = 2, max = 150, message = "Username must not be empty")
	private String username;
	
	@NotEmpty
	@Column(name="password")
	@Size(min = 8, message = "Password must be longer as 8 characters")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private UserRoles role;
	
	public User() {
		super();
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserRoles getRole() {
		return role;
	}

	public User setRole(UserRoles role) {
		this.role = role;
		return this;
	}
}
