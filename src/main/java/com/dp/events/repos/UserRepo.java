package com.dp.events.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dp.events.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	Optional<User> findUserByEmail(String email);
}
