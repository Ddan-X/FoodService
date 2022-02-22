package com.cogent.fooddeliveryapp.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotEmpty
	private Set<AddressRequest> address;
	
	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate doj;
	@NotEmpty
	private Set<String> roles;
}
//user role
//admin role
//role enum
//foodtype enum
//role dto;role id rolename(enum)
//repo-->role