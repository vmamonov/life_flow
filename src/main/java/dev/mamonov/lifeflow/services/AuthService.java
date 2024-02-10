package dev.mamonov.lifeflow.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.mamonov.lifeflow.dto.NewUserDto;
import dev.mamonov.lifeflow.models.User;
import dev.mamonov.lifeflow.repo.UsersRepo;
import dev.mamonov.lifeflow.security.UserRoles;

@Service
public class AuthService {
	private final UsersRepo usersRepo;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UsersRepo usersRepo, PasswordEncoder passwordEncoder) {
		this.usersRepo = usersRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void register(NewUserDto newUserDto) {
		String encodedPassword = passwordEncoder.encode(newUserDto.getPassword());
		User newUser = new User(newUserDto.getUsername(), encodedPassword);
		newUser.setRole(UserRoles.ROLE_USER);
		usersRepo.save(newUser);
	}
}
