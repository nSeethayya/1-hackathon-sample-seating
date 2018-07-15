package com.krishna.seatbooking.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventForm {

	private Long eventId;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	private String eventDate;
	private String location;
	private String state;
	private String userHosted;
	private boolean host;
	private boolean sameState;
	private boolean joined;
	private boolean creator;
	private List<User> joinedUsers;
	private String messages;

	@Data
	@AllArgsConstructor
	public static class User {
		private String name;
		private String location;
	}
}
