package com.krishna.seatbooking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krishna.seatbooking.dto.EventForm;
import com.krishna.seatbooking.dto.MessageForm;
import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.helper.UserDetailsHelper;
import com.krishna.seatbooking.service.EventService;
import com.krishna.seatbooking.service.UserService;

@CrossOrigin
@Controller
public class EventController {

	private UserService userService;
	private EventService eventService;
	private EventFormValidator eventFormValidator;

	/**
	 * @param userService
	 * @param eventService
	 */
	public EventController(UserService userService, EventService eventService, EventFormValidator eventFormValidator) {
		this.userService = userService;
		this.eventService = eventService;
		this.eventFormValidator = eventFormValidator;
	}
	
	@GetMapping(value = { "/", "events" })
	public String getEvents(Model model) {
		addEventsToModel(model);
		model.addAttribute("eventForm", new EventForm());
		String userName = UserDetailsHelper.findLoggedInUsername();
		model.addAttribute("username", userName);
		model.addAttribute("states", UserDetailsHelper.US_STATES);
		return "events";
	}

	@PostMapping("/events/create")
	public String createEvent(@ModelAttribute("eventForm") EventForm eventForm, Model model,
			BindingResult bindingResult) {
		eventFormValidator.validate(eventForm, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("states", UserDetailsHelper.US_STATES);
			addEventsToModel(model);
			String userName = UserDetailsHelper.findLoggedInUsername();
			model.addAttribute("username", userName);
			return "events";
		}
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.createEvent(eventForm, user);
		return "redirect:/events";
	}

	@GetMapping("/events/{eventId}/edit")
	public String findEvent(@PathVariable(value = "eventId") Long eventId, Model model) {
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		model.addAttribute("username", userName);
		model.addAttribute("eventForm", eventService.getEvent(eventId, user));
		model.addAttribute("states", UserDetailsHelper.US_STATES);
		return "editEvent";
	}

	@GetMapping("/events/{eventId}")
	public String findEventWall(@PathVariable(value = "eventId") Long eventId, Model model) {
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		model.addAttribute("username", userName);
		model.addAttribute("event", eventService.getEventWall(eventId, user));
		model.addAttribute("messageForm", new MessageForm());
		return "eventWall";
	}

	@RequestMapping("/events/edit")
	public String editEvent(@ModelAttribute("eventForm") EventForm eventForm, Model model,
			BindingResult bindingResult) {
		eventFormValidator.validate(eventForm, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("states", UserDetailsHelper.US_STATES);
			addEventsToModel(model);
			String userName = UserDetailsHelper.findLoggedInUsername();
			model.addAttribute("username", userName);
			return "editEvent";
		}
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.editEvent(eventForm, user);
		return "redirect:/events";
	}

	@RequestMapping("/events/join/{eventId}")
	public String joinEvent(@PathVariable(value = "eventId") Long eventId) {
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.joinEvent(eventId, user);
		return "redirect:/events";
	}

	@RequestMapping("/events/cancel/{eventId}")
	public String cancelEvent(@PathVariable(value = "eventId") Long eventId) {
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.cancelEvent(eventId, user);
		return "redirect:/events";
	}

	@RequestMapping("/events/delete/{eventId}")
	public String deleteEvent(@PathVariable(value = "eventId") Long eventId) {
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.deleteEvent(eventId, user);
		return "redirect:/events";
	}

	@RequestMapping("/events/addMessages/{eventId}")
	public String addMessages(@PathVariable(value = "eventId") Long eventId,
			@ModelAttribute("messageForm") MessageForm messageForm, Model model, BindingResult bindingResult) {
		if (StringUtils.isEmpty(messageForm.getMessage())) {
			bindingResult.rejectValue("message", "message");
			String userName = UserDetailsHelper.findLoggedInUsername();
			model.addAttribute("username", userName);
			User user = userService.findByUserName(userName);
			model.addAttribute("event", eventService.getEventWall(eventId, user));
			return "eventWall";
		}
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		eventService.addMessages(messageForm.getMessage(), eventId, user);
		return "redirect:/events/" + eventId;
	}

	private void addEventsToModel(Model model) {
		List<EventForm> sameStateEvents = new ArrayList<>();
		List<EventForm> otherStateEvents = new ArrayList<>();
		eventService.getEvents().parallelStream().forEach(evt -> {
			if (evt.isSameState())
				sameStateEvents.add(evt);
			else
				otherStateEvents.add(evt);

		});
		model.addAttribute("sameStateEvents", sameStateEvents);
		model.addAttribute("otherStateEvents", otherStateEvents);
	}
}
