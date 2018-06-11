package com.krishna.seatbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.seatbooking.dto.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
