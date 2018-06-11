package com.krishna.seatbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.seatbooking.dto.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	Optional<List<Section>> findBySeatsUserName(String userName);
}
