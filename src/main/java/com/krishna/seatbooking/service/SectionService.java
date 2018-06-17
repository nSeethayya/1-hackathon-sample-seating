package com.krishna.seatbooking.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.krishna.seatbooking.dto.Section;
import com.krishna.seatbooking.dto.SectionForm;

public interface SectionService {

	@Transactional
	void bookSeat(Long sectionId, Long seatId, String userName);

	List<Section> findAll();

	Optional<Section> findById(Long id);

	Optional<List<Section>> findBySeatsUserName(String userName);

	Set<SectionForm> findBookingHistory(String userName);
}
