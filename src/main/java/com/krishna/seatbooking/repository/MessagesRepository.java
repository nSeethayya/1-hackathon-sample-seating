package com.krishna.seatbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.seatbooking.dto.Messages;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

}
