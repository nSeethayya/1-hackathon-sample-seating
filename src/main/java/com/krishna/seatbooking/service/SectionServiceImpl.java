package com.krishna.seatbooking.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.seatbooking.dto.Section;
import com.krishna.seatbooking.repository.SectionRepository;

@Service
public class SectionServiceImpl implements SectionService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SectionRepository sectionRepository;

	/*
	 * public SectionServiceImpl(SectionRepository sectionRepository) {
	 * this.sectionRepository = sectionRepository; }
	 */

	public void bookSeat(Long sectionId, Long seatId, String userName) {
		Optional<Section> section = sectionRepository.findById(sectionId);
		if (section.isPresent()) {
			section.get().getSeats().stream().filter(seat -> seat.getId().equals(seatId)).forEach(seat -> {
				seat.setAvailable(false);
				seat.setUserName(userName);
			});
			sectionRepository.save(section.get());
		} else {
			LOGGER.warn("section not found for sectionId:" + sectionId + " seatId:" + seatId + " userName:" + userName);
		}
	}

}
