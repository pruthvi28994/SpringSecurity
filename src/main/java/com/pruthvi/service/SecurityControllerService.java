package com.pruthvi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pruthvi.entity.UserInfo;
import com.pruthvi.repository.UserDetailsEntityRepo;

@Service
public class SecurityControllerService {

	@Autowired
	UserDetailsEntityRepo entityRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void addUser() {
		//Adding User to DB
		List<UserInfo> userDetails =new ArrayList<UserInfo>(List.of(
				new UserInfo(null,"john", passwordEncoder.encode("pwd"), "ROLE_ADMIN"),
				new UserInfo(null,"smith", passwordEncoder.encode("pwd"), "ROLE_USER"),
				new UserInfo(null,"andy", passwordEncoder.encode("pwd"), "ROLE_STAFF")));
		System.out.println("**************  Added Dummy User Successfully ***********");
		entityRepo.saveAll(userDetails);
	}
}
