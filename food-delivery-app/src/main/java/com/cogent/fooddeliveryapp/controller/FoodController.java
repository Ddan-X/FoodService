package com.cogent.fooddeliveryapp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.dto.Food;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.FoodType;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.service.FoodService;


@RestController// same as @Controller add @ResponseBody 
@RequestMapping("/food")
@Validated// for the method Constraint
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	@PostMapping(value = "")
	public ResponseEntity<?> createFood(@Valid @RequestBody Food food) {
		//TODO: process POST request
		Food food2 = foodService.saveFood(food);
		
		return ResponseEntity.status(201).body(food2);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") @Min(1) Long id) {
		Food food = foodService.findFoodById(id).orElseThrow(()->new NoDataFoundException("no data found")); 
		return ResponseEntity.ok(food);
	}

	@GetMapping(value = "/")
	public ResponseEntity<?> getAllFoods() {
		List<Food> foods = foodService.findAllFoods();
		if(foods.size()>0) {
			return ResponseEntity.ok(foods);
		}else {
			 throw new NoDataFoundException("no foods found");
		}
	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFoodById(@PathVariable("id") Long id){
		
				if(foodService.existsByFoodId(id)) {
					foodService.deleteFoodById(id);
					return ResponseEntity.status(200).body("deleted");
				}else {
					
					throw new NoDataFoundException("no food found by this id");
				}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFood(@PathVariable("id") Long id, @RequestBody Food food){
		
				if(foodService.existsByFoodId(id)) {
					
					Optional<Food> food2 = foodService.findFoodById(id);
					food2.get().setFoodName(food.getFoodName());
					food2.get().setFoodCost(food.getFoodCost());
					food2.get().setFoodType(food.getFoodType());
					Food food3 = foodService.updateFoodById(food2.get());
					return ResponseEntity.status(200).body(food3);
				}else {
				
					throw new NoDataFoundException("no food found by this id");
				}
	}
	
	@GetMapping("/type/{foodType}")
	public ResponseEntity<?> findFoodByFoodType(@PathVariable("foodType") String foodType){
				FoodType f = FoodType.valueOf(foodType);
				List<Food> foods = foodService.findFoodsByType(f);
				if(foods.size()>0) {
					return ResponseEntity.ok(foods);
				}else {
					 throw new NoDataFoundException("no foods found");
				}
	}
	
	@GetMapping(value = "/all/asc")
	public ResponseEntity<?>  getAllDescOrder() {
		List<Food> list = foodService.findAllFoods();
		Collections.sort(list,(a,b)-> a.getFoodId().compareTo(b.getFoodId()));
		return ResponseEntity.status(200).body(list);
	}
	@GetMapping(value = "/all/desc")
	public ResponseEntity<?>  getAllAscOrder() {
		List<Food> list = foodService.findAllFoods();
		Collections.sort(list,(a,b)-> b.getFoodId().compareTo(a.getFoodId()));
		return ResponseEntity.status(200).body(list);
	}
	
	

}
