package com.cogent.fooddeliveryapp.service;

import java.util.List;
import java.util.Optional;

import com.cogent.fooddeliveryapp.dto.User;



public interface UserService {
	public User addUser(User user);
	public User updateUser(User user);
	public Optional<User> getUserById(long id);
	public List<User> getAllUsers();
	public String deleteUserById(long id);
	public boolean existsByUserId(long id);
}
