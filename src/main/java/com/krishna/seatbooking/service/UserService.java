package com.krishna.seatbooking.service;

import javax.transaction.Transactional;

import com.krishna.seatbooking.dto.User;

public interface UserService {
	
	@Transactional
	void save(User user);
	
	void autologin(String username, String password);

}
