package com.example.SpringJPA.Controller;

import com.example.SpringJPA.Model.Food;
import com.example.SpringJPA.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private FoodService foodService;

    // Home / Root Route
    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("foods", foodService.getPopularFoods());
        return "index";
    }

    // Menu Route
    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());
        return "menu";
    }

    // Orders Route
    @GetMapping("/orders")
    public String showOrdersPage(Model model, HttpSession session) {
        List<Long> orderFoodIds = getSessionIds(session, "orderFoodIds");
        model.addAttribute("orders", orderFoodIds.isEmpty()
                ? Collections.emptyList()
                : foodService.getFoodsByIds(orderFoodIds));
        return "orders";
    }

    @GetMapping("/cart")
    public String showCartPage(Model model, HttpSession session) {
        List<Long> cartFoodIds = getSessionIds(session, "cartFoodIds");
        model.addAttribute("cartItems", cartFoodIds.isEmpty()
                ? Collections.emptyList()
                : foodService.getFoodsByIds(cartFoodIds));
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session) {
        if (foodService.getFoodById(id).isPresent()) {
            List<Long> cartFoodIds = getSessionIds(session, "cartFoodIds");
            if (!cartFoodIds.contains(id)) {
                cartFoodIds.add(id);
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id, HttpSession session) {
        getSessionIds(session, "cartFoodIds").remove(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/place-order")
    public String placeOrder(HttpSession session) {
        List<Long> cartFoodIds = getSessionIds(session, "cartFoodIds");
        if (!cartFoodIds.isEmpty()) {
            getSessionIds(session, "orderFoodIds").addAll(cartFoodIds);
            cartFoodIds.clear();
        }
        return "redirect:/orders";
    }

    @SuppressWarnings("unchecked")
    private List<Long> getSessionIds(HttpSession session, String attributeName) {
        Object ids = session.getAttribute(attributeName);
        if (ids == null) {
            List<Long> newIds = new ArrayList<>();
            session.setAttribute(attributeName, newIds);
            return newIds;
        }
        return (List<Long>) ids;
    }

    // Save or Update Food
    @PostMapping("/food/save")
    public String saveFood(@ModelAttribute("food") Food food) {
        foodService.saveFood(food);
        return "redirect:/menu";
    }

    // Delete Food
    @GetMapping("/food/delete/{id}")
    public String deleteFood(@PathVariable("id") Long id) {
        foodService.deleteFood(id);
        return "redirect:/menu";
    }
}