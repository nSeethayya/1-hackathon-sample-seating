package com.krishna.seatbooking.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.krishna.seatbooking.dto.Role;
import com.krishna.seatbooking.dto.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSaveUser() {
		/*
		 * Role role = new Role(); role.setName("ADMIN"); role.setId(101L);
		 * entityManager.merge(role);
		 */
		User user = User.builder().userName("seethayya.n@hcl.com").firstName("seethayya").lastName("N").password("pwd")
				.country("IN").location("Hyd").createdAt(Calendar.getInstance().getTime()).enable(true)
				.updatedAt(Calendar.getInstance().getTime()).id(101L).build();
		List<Role> roles = roleRepository.findAll();
		entityManager.flush();
		user.setRoles(roles);
		entityManager.merge(user);
		User userDto = userRepository.findByUserName("seethayya.n@hcl.com");
		assertNotNull(userDto);
		assertEquals(user.getFirstName(), userDto.getFirstName());
		assertEquals(user.getLastName(), userDto.getLastName());
		assertEquals(user.getUserName(), userDto.getUserName());
		assertEquals(user.getEnable(), userDto.getEnable());
		assertEquals(user.getCountry(), userDto.getCountry());
		assertEquals(user.getLocation(), userDto.getLocation());
		assertEquals(user.getPassword(), userDto.getPassword());
		assertEquals(user.getRoles().size(), userDto.getRoles().size());
		assertEquals(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()),
				userDto.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()));
	}

}
