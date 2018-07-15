/**
 * 
 */
package com.krishna.seatbooking.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.krishna.seatbooking.dto.Event;
import com.krishna.seatbooking.dto.EventForm;
import com.krishna.seatbooking.dto.Messages;
import com.krishna.seatbooking.dto.User;
import com.krishna.seatbooking.helper.UserDetailsHelper;
import com.krishna.seatbooking.repository.EventRepository;
import com.krishna.seatbooking.repository.MessagesRepository;

/**
 * @author seethayya.n
 *
 */
@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;
	private MessagesRepository messagesRepository;
	private UserService userService;

	/**
	 * @param eventRepository
	 * @param messagesRepository
	 * @param userService
	 */
	public EventServiceImpl(EventRepository eventRepository, MessagesRepository messagesRepository,
			UserService userService) {
		this.eventRepository = eventRepository;
		this.messagesRepository = messagesRepository;
		this.userService = userService;
	}

	@Override
	public EventForm getEvent(Long eventId, User user) {
		return buildEventForm(user, eventRepository.getOne(eventId));
	}

	@Override
	public EventForm getEventWall(Long eventId, User user) {
		Event event = eventRepository.getOne(eventId);
		EventForm eventForm = buildEventForm(user, event);
		eventForm.setJoinedUsers(event.getUsers().parallelStream()
				.map(usr -> new EventForm.User(usr.getUserName(), usr.getLocation())).collect(Collectors.toList()));
		eventForm.setMessages(event.getMessages().stream()
				.map(msg -> findUserName(msg.getUserId()).concat(" says:").concat(msg.getMessage()))
				.collect(Collectors.joining("\n---------------\n")));
		return eventForm;
	}

	@Override
	public String createEvent(EventForm eventForm, User user) {
		Event event = Event.builder().createdAt(eventForm.getCreatedAt()).name(eventForm.getName())
				.host(user.getUserName()).location(eventForm.getLocation()).state(eventForm.getState())
				.users(Collections.singleton(user)).updatedAt(Calendar.getInstance().getTime()).build();
		eventRepository.save(event);
		return "success";
	}

	public String editEvent(EventForm eventForm, User user) {
		Optional<Event> event = eventRepository.findById(eventForm.getEventId());
		final StringBuilder response = new StringBuilder();
		event.ifPresent(evt -> {
			evt.setUpdatedAt(Calendar.getInstance().getTime());
			evt.setLocation(eventForm.getLocation());
			evt.setName(eventForm.getName());
			evt.setState(eventForm.getState());
			eventRepository.save(evt);
			response.append("success");
		});
		return response.toString();
	}

	public String joinEvent(Long eventId, User user) {
		Optional<Event> event = eventRepository.findById(eventId);
		event.ifPresent(evt -> {
			evt.getUsers().add(user);
			eventRepository.save(evt);
		});
		return "success";
	}

	public String cancelEvent(Long eventId, User user) {
		Optional<Event> event = eventRepository.findById(eventId);
		if (!event.isPresent())
			return "Event not found";

		Event evt = event.get();
		boolean removed = evt.getUsers().removeIf(usr -> {
			return usr.getId() == user.getId();
		});
		if (!removed)
			return "User not joined into this event to cancel";
		eventRepository.save(evt);
		return "success";
	}

	public String deleteEvent(Long eventId, User user) {
		Optional<Event> event = eventRepository.findById(eventId);
		final StringBuilder response = new StringBuilder();
		event.ifPresent(evt -> {
			if (evt.getHost().equals(user.getUserName())) {
				eventRepository.deleteById(eventId);
				response.append("success");
			} else {
				response.append("User not authorized to delete");
			}
		});
		return response.toString();
	}

	public String addMessages(String message, Long eventId, User user) {
		Optional<Event> event = eventRepository.findById(eventId);
		final StringBuilder response = new StringBuilder();
		event.ifPresent(evt -> {
			Messages messages = new Messages();
			messages.setCreatedAt(Calendar.getInstance().getTime());
			messages.setMessage(message);
			messages.setUserId(user.getUserName());
			messages.setEvent(evt);
			messagesRepository.save(messages);
			response.append("success");
		});
		return response.toString();
	}

	public List<EventForm> getEvents() {
		List<Event> events = eventRepository.findAll();
		String userName = UserDetailsHelper.findLoggedInUsername();
		User user = userService.findByUserName(userName);
		return events.parallelStream().map(evt -> buildEventForm(user, evt)).collect(Collectors.toList());
	}

	private EventForm buildEventForm(User user, Event event) {
		return EventForm.builder().createdAt(event.getCreatedAt())
				.eventDate(LocalDateTime.ofInstant(event.getCreatedAt().toInstant(), ZoneId.systemDefault())
						.format(UserDetailsHelper.DATE_FORMAT))
				.eventId(event.getId()).name(event.getName()).host(event.getHost().equals(user.getUserName()))
				.userHosted(findUserName(event.getHost())).location(event.getLocation()).location(event.getLocation())
				.state(event.getState()).state(event.getState())
				.sameState(user.getCountry().equalsIgnoreCase(event.getState()))
				.creator(user.getUserName().equals(event.getHost())).joined(isUserJoinedEvent(user, event)).build();
	}

	private boolean isUserJoinedEvent(User user, Event evt) {
		return evt.getUsers().stream().filter(usr -> usr.getId() == user.getId()).findAny().isPresent();
	}

	private String findUserName(String userId) {
		User user = userService.findByUserName(userId);
		if (user != null)
			return user.getFirstName().concat(" ").concat(user.getLastName());
		else
			return null;
	}
}
