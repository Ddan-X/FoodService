package com.cogent.fooddeliveryapp.utils;

import java.util.HashSet;
import java.util.Set;

import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.payload.request.AddressRequest;
import com.cogent.fooddeliveryapp.payload.response.UserResponse;

public class Builder {

	//userbuilder
	public UserResponse buildUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(user.getEmail());
		userResponse.setName(user.getUsername());
		userResponse.setDoj(user.getDoj());
		Set<String> roles = new HashSet<>();
		user.getRoles().forEach(e2->{
			roles.add(e2.getRoleName().name());
		});
		Set<AddressRequest> addressRequests = new HashSet<>();
		user.getAddresses().forEach(e3->{
			AddressRequest address = new AddressRequest();
			address.setHouseNumber(e3.getHouseNumber());
			address.setCity(e3.getCity());
			address.setCountry(e3.getCountry());
			address.setState(e3.getState());
			address.setStreet(e3.getStreet());
			address.setZip(e3.getZip());
			addressRequests.add(address);
		});
		userResponse.setAddress(addressRequests);
		userResponse.setRoles(roles);
		return userResponse;
	}
}
