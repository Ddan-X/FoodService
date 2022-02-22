package com.cogent.fooddeliveryapp.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.cogent.fooddeliveryapp.enums.FoodType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long foodId;
	
	@NotBlank
	private String foodName;
	
	@NotNull
	private float foodCost;
	
	
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String foodPic;
}
