package com.pruthvi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruthvi.service.SecurityControllerService;

@RestController
public class SecurityController {
	
	@Autowired
	SecurityControllerService securityService;
	
	@GetMapping("/Home")
	public String getHome() {
		return "Welcome To Spring Security";
	}
	/**
	 * @implNote Always specify ROLE_ within hasAuthority if using In-memory
	 * @return
	 */
	@GetMapping("/Admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getAdminScreen() {
		return "This is Admin screen";
	}
	
	@GetMapping("/EmployeeDashBoard")
	@PreAuthorize("hasAuthority('ROLE_STAFF')")
	public String getEmployeeDash() {
		return "This is Employee Dashboard Screen";
	}
	
	/**
	 * Will Be used for Type 2 Authentication 
	 * i.e UserDetailsService Authentication (Call this API before checking other end points
	 * @return
	 */
	@GetMapping("/AddUser")
	public String saveUser() {
		securityService.addUser();
		return "Added User Successfully";
	}
}
