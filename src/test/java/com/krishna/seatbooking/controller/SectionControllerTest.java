package com.krishna.seatbooking.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.seatbooking.dto.Seat;
import com.krishna.seatbooking.dto.Section;
import com.krishna.seatbooking.service.SectionService;

@RunWith(SpringRunner.class)
@WebMvcTest(SectionController.class)
public class SectionControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private SectionService sectionService;
	@MockBean
	private SectionFormValidator sectionFormValidator;
	private JacksonTester<List<Seat>> seatsJson;
	private JacksonTester<List<Section>> sectionsJson;

	@Before
	public void initialize() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	@MockUser
	public void testHomeController() throws Exception {
		Section section = new Section();
		section.setId(1L);
		section.setLayoutInfo("1_1_1");
		section.setName("1");
		List<Section> sections = Stream.of(section).collect(Collectors.toList());
		given(this.sectionService.findAll()).willReturn(sections);

		ResultActions result = this.mvc.perform(get("/")).andExpect(MockMvcResultMatchers.status().isOk());
		System.out.println("Result:" + result);
		Map<String, Object> responseModelMap = result.andReturn().getModelAndView().getModel();
		System.err.println(responseModelMap.get("sections"));
		assertThat(responseModelMap.get("sections")).isNotNull();
		assertEquals(sections, responseModelMap.get("sections"));

	}

	@Test
	@MockUser
	public void findSeats() throws Exception {
		Section section = new Section();
		section.setId(1L);
		section.setLayoutInfo("1_1_1");
		section.setName("1");
		Seat seat = new Seat();
		seat.setAvailable(true);
		seat.setId(1L);
		seat.setName("1_1");
		List<Seat> seats = Stream.of(seat).collect(Collectors.toList());
		section.setSeats(seats);
		given(this.sectionService.findById(1L)).willReturn(Optional.of(section));

		ResultActions result = this.mvc.perform(get("/findSeats/1")).andExpect(MockMvcResultMatchers.status().isOk());
		System.out.println("Result:" + result);
		assertThat(this.seatsJson.write(seats)).isEqualToJson(result.andReturn().getResponse().getContentAsByteArray());
		//result.andExpect(jsonPath(".deprecated", Matchers.equalTo("")));
	}

}
