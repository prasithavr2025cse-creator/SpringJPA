package com.example.SpringJPA.Service;

import com.example.SpringJPA.Model.Food;
import com.example.SpringJPA.Repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // Handles both Create (if id is null) and Update (if id is provided)
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    // Fetch all food items
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public List<Food> getPopularFoods() {
        return foodRepository.findAll().stream().limit(3).collect(Collectors.toList());
    }

    public List<Food> getFoodsByIds(List<Long> ids) {
        return foodRepository.findAllById(ids);
    }

    // Fetch a single food item by ID
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    // Delete a food item by ID
    public void deleteFood(Long id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
        }
    }
}