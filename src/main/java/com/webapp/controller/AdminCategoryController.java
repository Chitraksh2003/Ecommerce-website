package com.webapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.webapp.entity.Category;
import com.webapp.service.CategoryService;

@Controller
public class AdminCategoryController {
	
	@Autowired
	CategoryService service;
	
	@GetMapping("/admin")
	public String getAdminHome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCatogeries(Model model) {
		model.addAttribute("categories",service.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCatogeriesAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatogeries(@ModelAttribute Category category) {
		service.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCatogeries(@PathVariable int id) {
		service.deleteCategory(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCatogeries(@PathVariable int id, Model model) {
		Optional<Category> category = service.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}else {
			return "404";
		}
	}
	
}

