package com.krishna.seatbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.seatbooking.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {

	//@Query(value = "select * from \"users\" where user_name = ?1", nativeQuery = true)
	User findByUserName(String userName);
}
