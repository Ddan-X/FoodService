package com.cogent.fooddeliveryapp.service;

import java.util.List;
import java.util.Optional;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.enums.FoodType;



public interface FoodService {
	public Food saveFood(Food food);
	public Optional<Food> findFoodById(Long id);
	public boolean existsByFoodId(Long id);
	public Food updateFoodById(Food food);
	public void deleteFoodById(Long id);
	public List<Food> findAllFoods();
	public List<Food> findFoodsByType(FoodType foodtype);
	
}
