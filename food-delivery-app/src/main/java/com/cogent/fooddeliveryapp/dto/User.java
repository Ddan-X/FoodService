package com.cogent.fooddeliveryapp.dto;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"addresses","roles"})
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"addresses","roles"})//do not need addresses,roles at toString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate doj = LocalDate.now();
	
	//lazy will not load addresses
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Address> addresses;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="id"))
	public Set<Role> roles = new HashSet<>();
	
	
	
	
}
