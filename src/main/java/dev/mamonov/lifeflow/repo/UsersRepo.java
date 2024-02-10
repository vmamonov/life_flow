package dev.mamonov.lifeflow.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.mamonov.lifeflow.models.User;

@Repository
public interface UsersRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username); 
}
