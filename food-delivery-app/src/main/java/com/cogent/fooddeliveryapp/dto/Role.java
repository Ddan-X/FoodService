package com.cogent.fooddeliveryapp.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cogent.fooddeliveryapp.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	private long roleId;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RoleType roleName;
	
	
}
