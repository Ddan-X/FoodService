package com.cogent.fooddeliveryapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.enums.FoodType;
import com.cogent.fooddeliveryapp.repository.FoodRepository;
import com.cogent.fooddeliveryapp.service.FoodService;



@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepository foodRepo;
	
	@Override
	public Food saveFood(Food food) {
		// TODO Auto-generated method stub
		return foodRepo.save(food);
	}

	@Override
	public Optional<Food> findFoodById(Long id) {
		// TODO Auto-generated method stub
		return foodRepo.findById(id);
	}

	@Override
	public Food updateFoodById(Food food) {
		// TODO Auto-generated method stub
		return foodRepo.save(food);
	}

	@Override
	public List<Food> findAllFoods() {
		// TODO Auto-generated method stub
		return foodRepo.findAll();
	}

	@Override
	public List<Food> findFoodsByType(FoodType foodtype) {
		// TODO Auto-generated method stub
		return foodRepo.findByFoodType(foodtype);
	}

	@Override
	public boolean existsByFoodId(Long id) {
		// TODO Auto-generated method stub
		return foodRepo.existsById(id);
	}

	@Override
	public void deleteFoodById(Long id) {
		// TODO Auto-generated method stub
		foodRepo.deleteById(id);
	}

}
