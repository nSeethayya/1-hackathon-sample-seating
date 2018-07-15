package com.krishna.seatbooking.helper;

import java.time.format.DateTimeFormatter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsHelper {

	public static final String[] US_STATES = new String[] { "AL", "AK", "AZ", "AR", "AA", "AE", "AP", "CA", "CO", "CT",
			"DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
			"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
			"TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY" };

	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

	public static String findLoggedInUsername() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		}
	}
}
