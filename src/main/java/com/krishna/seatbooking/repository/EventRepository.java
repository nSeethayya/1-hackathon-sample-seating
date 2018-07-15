package com.krishna.seatbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.seatbooking.dto.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
