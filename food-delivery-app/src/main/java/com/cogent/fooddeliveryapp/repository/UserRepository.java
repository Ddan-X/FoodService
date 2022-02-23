package com.cogent.fooddeliveryapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.fooddeliveryapp.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUserName(String name);
	public Optional<User> findByEmail(String email);
	
}
