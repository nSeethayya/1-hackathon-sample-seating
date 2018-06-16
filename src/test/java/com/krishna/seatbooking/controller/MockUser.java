package com.krishna.seatbooking.controller;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RUNTIME)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public @interface MockUser {
}
