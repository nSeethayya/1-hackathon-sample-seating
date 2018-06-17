package com.krishna.seatbooking.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SectionForm {

	private Long sectionId;
	private String sectionName;
	private Long seatId;
	private String seatName;
	private BigDecimal pricePerTicket;
	private BigDecimal totalCost;
}
