package com.pruthvi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruthvi.entity.UserInfo;

@Repository
public interface UserDetailsEntityRepo extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByUserName(String username);

}
