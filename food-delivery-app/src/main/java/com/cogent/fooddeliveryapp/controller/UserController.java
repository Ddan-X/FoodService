package com.cogent.fooddeliveryapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.cogent.fooddeliveryapp.dto.Address;
import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.RoleType;
import com.cogent.fooddeliveryapp.exceptions.IdNotFoundException;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.payload.request.AddressRequest;
import com.cogent.fooddeliveryapp.payload.request.SignupRequest;
import com.cogent.fooddeliveryapp.payload.response.UserResponse;
import com.cogent.fooddeliveryapp.repository.RoleRepository;
import com.cogent.fooddeliveryapp.service.UserService;
import com.cogent.fooddeliveryapp.utils.Builder;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {
		//
		Set<Role> roles = new HashSet<>();
		signupRequest.getRoles().forEach(e -> {
			if (signupRequest.getRoles() == null) {
				Role userRole = roleRepository.findByRoleName(RoleType.ROLE_USER)
						.orElseThrow(() -> new IdNotFoundException("role id not found exception"));
				roles.add(userRole);
			}
			switch (e) {
			case "user":
				Role userRole = roleRepository.findByRoleName(RoleType.ROLE_USER)
						.orElseThrow(() -> new IdNotFoundException("role id not found exception"));
				roles.add(userRole);
				break;
			case "admin":
				Role adminRole = roleRepository.findByRoleName(RoleType.ROLE_ADMIN)
						.orElseThrow(() -> new IdNotFoundException("role id not found exception"));
				roles.add(adminRole);
				break;
			default:
				break;
			}

		});

		User user = new User();

		Set<Address> addresses = new HashSet<Address>();
		signupRequest.getAddress().forEach(e -> {
			Address address = new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
			address.setUser(user);
			address.setZip(e.getZip());
			addresses.add(address);
		});

		user.setAddresses(addresses);
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
		user.setRoles(roles);
		user.setDoj(signupRequest.getDoj());

		User u = userService.addUser(user);

		return ResponseEntity.status(201).body(u);

	}

	@GetMapping("/")
	public ResponseEntity<?> findAllUsers() {
		List<User> ulist = userService.getAllUsers();
		List<UserResponse> userResponses = new ArrayList<>();
		Builder builder = new Builder();
		ulist.forEach(e -> {
			UserResponse userResponse = builder.buildUserResponse(e);
//			userResponse.setEmail(e.getEmail());
//			userResponse.setName(e.getName());
//			userResponse.setDoj(e.getDoj());
//			Set<String> roles = new HashSet<>();
//			e.getRoles().forEach(e2 -> {
//				roles.add(e2.getRoleName().name());
//			});
//			Set<AddressRequest> addressRequests = new HashSet<>();
//			e.getAddresses().forEach(e3 -> {
//				AddressRequest address = new AddressRequest();
//				address.setHouseNumber(e3.getHouseNumber());
//				address.setCity(e3.getCity());
//				address.setCountry(e3.getCountry());
//				address.setState(e3.getState());
//				address.setStreet(e3.getStreet());
//				address.setZip(e3.getZip());
//				addressRequests.add(address);
//			});
//			userResponse.setAddress(addressRequests);
//			userResponse.setRoles(roles);
			userResponses.add(userResponse);

		});
		if (userResponses.size() > 0) {
			return ResponseEntity.ok(userResponses);
		} else {
			throw new NoDataFoundException("no users are there");
		}

	}

	@GetMapping(value="/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
		User user = userService.getUserById(id).orElseThrow(()->new NoDataFoundException("no user find"));
		
			Builder builder = new Builder();
			UserResponse userResponse = builder.buildUserResponse(user);
			
//			userResponse.setEmail(user.getEmail());
//			userResponse.setName(user.getName());
//			userResponse.setDoj(user.getDoj());
//			Set<String> roles = new HashSet<>();
//			user.getRoles().forEach(e2->{
//				roles.add(e2.getRoleName().name());
//			});
//			Set<AddressRequest> addressRequests = new HashSet<>();
//			user.getAddresses().forEach(e3->{
//				AddressRequest address = new AddressRequest();
//				address.setHouseNumber(e3.getHouseNumber());
//				address.setCity(e3.getCity());
//				address.setCountry(e3.getCountry());
//				address.setState(e3.getState());
//				address.setStreet(e3.getStreet());
//				address.setZip(e3.getZip());
//				addressRequests.add(address);
//			});
//			userResponse.setAddress(addressRequests);
//			userResponse.setRoles(roles);

		
		return ResponseEntity.status(200).body(userResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?>deleteByUserId(@PathVariable("id") Long id){
		//exist user by id?
		if(userService.existsByUserId(id)) {
			userService.deleteUserById(id);
			return ResponseEntity.noContent().build();
		}else {
			//fail,no user found
			throw new NoDataFoundException("no user found by this id");
		}
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user){
		//exist user by id?
				if(userService.existsByUserId(id)) {
					User u = userService.getUserById(id).get();
					u.setEmail(user.getEmail());
					u.setName(user.getName());
					User u2 = userService.addUser(u);
					return ResponseEntity.status(200).body(u2);
				}else {
					//fail,no user found
					throw new NoDataFoundException("no user found by this id");
				}
	}
}
