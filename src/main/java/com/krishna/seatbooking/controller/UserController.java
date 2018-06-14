/**
 * 
 */
package com.krishna.seatbooking.controller;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.dto.UserForm;
import com.krishna.seatbooking.service.UserDetailsServiceImpl;
import com.krishna.seatbooking.service.UserService;

/**
 * @author seethayya.n
 *
 */
@CrossOrigin
@Controller
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private UserService userService;
	private UserValidator userValidator;

	public UserController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}

	@GetMapping(value = "/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("countries", addCounties());
		return "registration";
	}

	public List<String> addCounties() {
		return Stream.of("IN", "US", "UK").collect(Collectors.toList());
	}

	@PostMapping(value = "/registration")
	public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult,
			Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("countries", addCounties());
			return "registration";
		}
		userService.save(buildUser(userForm));
		userService.autologin(userForm.getEmail(), userForm.getPassword());

		return "redirect:/home";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	private User buildUser(UserForm userForm) {
		User user = User.builder().updatedAt(Calendar.getInstance().getTime()).userName(userForm.getEmail())
				.firstName(userForm.getFirstName()).lastName(userForm.getLastName()).enable(true)
				.location(userForm.getLocation()).country(userForm.getCountry()).password(userForm.getPassword())
				.createdAt(Calendar.getInstance().getTime()).build();
		return user;
	}

}
