/**
 * 
 */
package com.krishna.seatbooking.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.dto.UserForm;
import com.krishna.seatbooking.helper.UserDetailsHelper;
import com.krishna.seatbooking.service.UserService;

/**
 * @author seethayya.n
 *
 */
@CrossOrigin
@Controller
public class UserController {

	private UserService userService;
	private UserFormValidator userFormValidator;

	public UserController(UserService userService, UserFormValidator userFormValidator) {
		this.userService = userService;
		this.userFormValidator = userFormValidator;
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("countries", UserDetailsHelper.US_STATES);
		return "login";
	}

	@PostMapping(value = "/registration")
	public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult,
			Model model) {
		userFormValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("countries", UserDetailsHelper.US_STATES);
			return "login";
		}
		userService.save(buildUser(userForm));
		// userService.autologin(userForm.getEmail(), userForm.getPassword());
		return "redirect:/login";
	}

	private User buildUser(UserForm userForm) {
		User user = User.builder().updatedAt(Calendar.getInstance().getTime()).userName(userForm.getEmail())
				.firstName(userForm.getFirstName()).lastName(userForm.getLastName()).enable(true)
				.location(userForm.getLocation()).country(userForm.getCountry()).password(userForm.getPassword())
				.createdAt(Calendar.getInstance().getTime()).build();
		return user;
	}
}