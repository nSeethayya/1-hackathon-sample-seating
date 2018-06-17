package com.krishna.seatbooking.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.krishna.seatbooking.dto.Seat;
import com.krishna.seatbooking.dto.Section;
import com.krishna.seatbooking.dto.SectionForm;
import com.krishna.seatbooking.repository.SectionRepository;

@Service
public class SectionServiceImpl implements SectionService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private SectionRepository sectionRepository;

	@Value("#{new java.math.BigDecimal('${ticket.price}')}")
	private BigDecimal ticketPrice;
	@Value("#{new java.math.BigDecimal('${tax.percentage}')}")
	private BigDecimal taxPercentage;
	@Value("#{new java.math.BigDecimal('${convenience.charge}')}")
	private BigDecimal convenienceCharge;

	public SectionServiceImpl(SectionRepository sectionRepository) {
		this.sectionRepository = sectionRepository;
	}

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

	public List<Section> findAll() {
		return sectionRepository.findAll();
	}

	public Optional<Section> findById(Long id) {
		return sectionRepository.findById(id);
	}

	public Optional<List<Section>> findBySeatsUserName(String userName) {
		return sectionRepository.findBySeatsUserName(userName);
	}

	public Set<SectionForm> findBookingHistory(String userName) {
		Optional<List<Section>> bookedSeats = findBySeatsUserName(userName);
		Set<SectionForm> bookingHistory = new HashSet<>();
		if (bookedSeats.isPresent()) {
			List<Section> sections = bookedSeats.get();
			bookingHistory = sections.stream().flatMap(s -> s.getSeats().stream())
					.filter(seat -> seat.getUserName() != null && seat.getUserName().equals(userName))
					.map(seat -> buildSectionForm(seat)).collect(Collectors.toSet());
		}
		return bookingHistory;
	}

	private SectionForm buildSectionForm(Seat seat) {
		SectionForm sectionForm = new SectionForm();
		sectionForm.setSectionName(seat.getSection().getName());
		sectionForm.setSeatName(seat.getName());
		sectionForm.setPricePerTicket(ticketPrice);
		BigDecimal taxAmount = ticketPrice.multiply(taxPercentage).divide(BigDecimal.valueOf(100),
				RoundingMode.HALF_UP);
		sectionForm.setTotalCost(ticketPrice.add(taxAmount).add(convenienceCharge));
		return sectionForm;
	}

}
