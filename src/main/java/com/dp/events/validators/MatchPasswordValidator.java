package com.dp.events.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dp.events.annotation.MatchPassword;
import com.dp.events.models.User;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, User>{
	
	@Override
	public void initialize(MatchPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if(user.getPasswordConfirm()==null) {
			return true;
		}
		return user.getPassword().equals(user.getPasswordConfirm());
	}
}