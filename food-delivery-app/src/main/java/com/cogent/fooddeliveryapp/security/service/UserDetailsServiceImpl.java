package com.cogent.fooddeliveryapp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserName(username)
				.orElseThrow(() -> new NoDataFoundException("user not find with username"+username));
	
		return UserDetailsImpl.build(user);
	
	}

}
