package com.dp.events.validators;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.dp.events.annotation.UniqueEmail;
import com.dp.events.models.User;
import com.dp.events.repos.UserRepo;

public class EmailValidator implements ConstraintValidator<UniqueEmail, String>{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void initialize(UniqueEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if(userRepo == null) {
			return true;
		}
		Optional<User> optionalUser = userRepo.findUserByEmail(email);
		if(optionalUser.isPresent()) {
			return false;
		}else {
			return true;
		}
	}
}
