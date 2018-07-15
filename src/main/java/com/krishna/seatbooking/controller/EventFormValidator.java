/**
 * 
 */
package com.krishna.seatbooking.controller;

import java.util.Calendar;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.krishna.seatbooking.dto.EventForm;

/**
 * @author seethayya.n
 *
 */
@Component
public class EventFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return EventForm.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "NotEmpty");
		if (((EventForm) o).getCreatedAt() == null)
			errors.rejectValue("createdAt", "notEmpty.eventForm.createdAt");
		else if (((EventForm) o).getCreatedAt().compareTo(Calendar.getInstance().getTime()) <= 0)
			errors.rejectValue("createdAt", "pastDate.eventForm.createdAt");

	}
}
