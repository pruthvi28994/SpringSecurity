package com.pruthvi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pruthvi.entity.UserInfo;
import com.pruthvi.entity.UserInfoUserDetails;
import com.pruthvi.repository.UserDetailsEntityRepo;

/**
 * Userinfo details Service is responsible for loading User Name & Password from DB
 * Use of UserInfoDetailsService is to return UserDetails o security Config
 */
@Service
public class UserInfoDetailsService implements UserDetailsService {

	@Autowired
	private UserDetailsEntityRepo userDetailRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Calling Data to check if user is present or not
		Optional<UserInfo> userPresent = userDetailRepo.findByUserName(username);
		/**
		 * If UserInfo is present then call UserInfoUserDetails class 
		 */
		return userPresent.map(UserInfoUserDetails :: new ).orElseThrow(()->new UsernameNotFoundException("User Details No Found"));
	}

}
