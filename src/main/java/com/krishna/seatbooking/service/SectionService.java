package com.krishna.seatbooking.service;

import javax.transaction.Transactional;

public interface SectionService {

	@Transactional
	void bookSeat(Long sectionId, Long seatId, String userName);

}
