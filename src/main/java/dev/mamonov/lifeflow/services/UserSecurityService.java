package dev.mamonov.lifeflow.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.mamonov.lifeflow.models.User;
import dev.mamonov.lifeflow.repo.UsersRepo;
import dev.mamonov.lifeflow.security.UserSecurityDetails;

@Service
public class UserSecurityService implements UserDetailsService {
	private final UsersRepo usersRepo;

	public UserSecurityService(UsersRepo usersRepo) {
		super();
		this.usersRepo = usersRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = usersRepo.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserSecurityDetails(user.get());
	}
}
