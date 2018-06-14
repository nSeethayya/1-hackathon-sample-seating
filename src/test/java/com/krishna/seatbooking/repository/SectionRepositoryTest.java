package com.krishna.seatbooking.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.krishna.seatbooking.dto.Seat;
import com.krishna.seatbooking.dto.Section;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SectionRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private SectionRepository sectionRepository;

	@Test
	public void testSaveUser() {
		Section section = new Section();
		section.setLayoutInfo("1_2");
		section.setName("1");
		Seat seat = new Seat();
		seat.setAvailable(true);
		seat.setName("1_1_1");
		section.setSeats(Stream.of(seat).collect(Collectors.toList()));
		entityManager.persist(section);
		List<Section> sections = sectionRepository.findAll();
		assertNotNull(sections);
		Optional<Section> sectionOpt = sections.stream().filter(s -> section.getName().equals(s.getName())).findAny();
		assertTrue(sectionOpt.isPresent());
		Section sectionDto = sectionOpt.get();
		assertEquals(section.getName(), sectionDto.getName());
		assertEquals(section.getLayoutInfo(), sectionDto.getLayoutInfo());
		assertEquals(section.getName(), sectionDto.getName());
	
		assertEquals(section.getSeats().size(), sectionDto.getSeats().size());
		assertEquals(section.getSeats().stream().map(s -> s.getName()).collect(Collectors.toList()),
				sectionDto.getSeats().stream().map(s -> s.getName()).collect(Collectors.toList()));
	}

}
