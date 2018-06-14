/**
 * 
 */
package com.krishna.seatbooking.controller;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.krishna.seatbooking.dto.UserForm;

/**
 * @author seethayya.n
 *
 */
@Component
public class UserValidator implements Validator {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public boolean supports(Class<?> aClass) {
		return UserForm.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserForm user = (UserForm) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		try {
			if (userDetailsService.loadUserByUsername(user.getEmail()) != null) {
				errors.rejectValue("email", "Duplicate.userForm.userName");
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		if (!(pattern.matcher(user.getEmail()).matches())) {
			errors.rejectValue("email", "user.email.invalid");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPasswordConfirm() == null || !user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}

	}
}
