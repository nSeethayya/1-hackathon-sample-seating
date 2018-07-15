/**
 * 
 */
package com.krishna.seatbooking.service;

import java.util.List;

import javax.transaction.Transactional;

import com.krishna.seatbooking.dto.EventForm;
import com.krishna.seatbooking.dto.User;

/**
 * @author seethayya.n
 *
 */
public interface EventService {

	@Transactional
	String createEvent(EventForm eventForm, User user);

	@Transactional
	String editEvent(EventForm eventForm, User user);

	@Transactional
	String joinEvent(Long eventId, User user);

	@Transactional
	String cancelEvent(Long eventId, User user);

	@Transactional
	String deleteEvent(Long eventId, User user);

	@Transactional
	String addMessages(String message, Long eventId, User user);

	List<EventForm> getEvents();

	EventForm getEvent(Long eventId, User user);

	EventForm getEventWall(Long eventId, User user);
}
