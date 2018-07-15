package com.krishna.seatbooking.dto;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date createdAt;
	private String name;
	private String location;
	private String state;
	private Date updatedAt;
	private String host;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "event_members", inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
	private Collection<User> users;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private Collection<Messages> messages;
}
