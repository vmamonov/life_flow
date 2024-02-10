package dev.mamonov.lifeflow.services;

import org.springframework.stereotype.Service;

import dev.mamonov.lifeflow.models.User;
import dev.mamonov.lifeflow.repo.UsersRepo;

@Service
public class UserService {
	private final UsersRepo userRepo;
	
	public UserService(UsersRepo userRepo) {
		this.userRepo = userRepo;
	}

	public void save(User user) {
		userRepo.save(user);
	}
}
