package com.webapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.Dto.ProductDTO;
import com.webapp.entity.Product;
import com.webapp.service.CategoryService;
import com.webapp.service.ProductsService;

@Controller
public class AdminProductController {
	
	public String uploadDir= System.getProperty("user.dir") + "/src/main/resources/static/ProductImages";

	@Autowired
	ProductsService productService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/admin/products")
	public String getProduct(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@GetMapping("/admin/products/add")
	public String addProduct(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}

	@PostMapping("/admin/products/add")
	public String postAddproduct(@ModelAttribute ProductDTO productdto,
			@RequestParam("productImage") MultipartFile multiPartFile, @RequestParam("imgName") String imgName) throws IOException {

		String imageUUID = null;
		if (!multiPartFile.isEmpty()) {
			imageUUID = multiPartFile.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, multiPartFile.getBytes());
		}else {
			imageUUID = imgName;
		}

		Product product = Product.builder().id(productdto.getId()).name(productdto.getName())
				.description(productdto.getDescription()).price(productdto.getPrice()).weight(productdto.getWeight())
				.imageName(imageUUID).category(categoryService.getCategoryById(productdto.getCategoryId()).get())
				.build();
		
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateproduct(@PathVariable int id, Model model)  {
		Product product = productService.getProductById(id);
		ProductDTO productdto = new ProductDTO();
		
		productdto.setCategoryId(product.getCategory().getId());
		productdto.setDescription(product.getDescription());
		productdto.setId(product.getId());
		productdto.setImageName(product.getImageName());
		productdto.setName(product.getName());
		productdto.setPrice(product.getPrice());
		productdto.setWeight(product.getWeight());
				
		model.addAttribute("productDTO",productdto );
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
}
