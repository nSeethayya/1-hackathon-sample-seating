package com.krishna.seatbooking.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krishna.seatbooking.dto.Seat;
import com.krishna.seatbooking.dto.Section;
import com.krishna.seatbooking.dto.SectionForm;
import com.krishna.seatbooking.helper.UserDetailsHeper;
import com.krishna.seatbooking.repository.SectionRepository;
import com.krishna.seatbooking.service.SectionServiceImpl;

import lombok.val;

@CrossOrigin
@Controller
public class SectionController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private SectionRepository sectionsRepository;
	private SectionServiceImpl sectionService;

	public SectionController(SectionRepository sectionsRepository, SectionServiceImpl sectionService) {
		this.sectionsRepository = sectionsRepository;
		this.sectionService = sectionService;
	}

	@RequestMapping(value = { "/", "/home" })
	public String home(Model model) {
		val x = sectionsRepository.findAll();
		model.addAttribute("sections", x);
		String userName = UserDetailsHeper.findLoggedInUsername();
		model.addAttribute("username", userName);
		model.addAttribute("sectionForm", new SectionForm());
		Optional<List<Section>> bookedSeats = sectionsRepository.findBySeatsUserName(userName);
		Set<SectionForm> bookingHistory = new HashSet<>();
		if (bookedSeats.isPresent()) {
			List<Section> sections = bookedSeats.get();
			bookingHistory = sections.stream().flatMap(s -> s.getSeats().stream())
					.filter(seat -> seat.getUserName() != null && seat.getUserName().equals(userName))
					.map(seat -> buildSectionForm(seat)).collect(Collectors.toSet());
		}

		model.addAttribute("bookingHistory", bookingHistory);
		return "home";
	}

	@GetMapping(value = "/findSeats/{sectionId}")
	@ResponseBody
	public List<Seat> findSeats(@PathVariable(value = "sectionId") Long sectionId, Model model) {
		val x = sectionsRepository.findById(sectionId);
		return x.get().getSeats();
	}

	@RequestMapping(value = "/bookTickets")
	public String bookTickets(@ModelAttribute("sectionForm") SectionForm sectionForm, BindingResult bindingResult,
			Model model) {
		sectionService.bookSeat(sectionForm.getSectionId(), sectionForm.getSeatId(),
				UserDetailsHeper.findLoggedInUsername());
		return "redirect:/home";
	}

	private SectionForm buildSectionForm(Seat seat) {
		SectionForm sectionForm = new SectionForm();
		sectionForm.setSectionName(seat.getSection().getName());
		sectionForm.setSeatName(seat.getName());
		return sectionForm;
	}
}