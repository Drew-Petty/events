package com.dp.events.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dp.events.models.User;
import com.dp.events.repos.UserRepo;

@Service
public class UserService {
	private final UserRepo userRepo;
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	public List<User> allUsers(){
		return (List<User>) userRepo.findAll();
	}
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	public User findUserById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
	public User findUserByEmail(String email) {
		Optional<User> optionalUser = userRepo.findUserByEmail(email);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
	public User registerUser(User u) {
		String hash = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
		u.setPassword(hash);
		u.setPasswordConfirm(hash);
		return userRepo.save(u);
	}
	public boolean authenticate(String pass, User user) {
		if(BCrypt.checkpw(pass, user.getPassword())) {
			return true;
		}else {
			return false;
		}
	}
}
