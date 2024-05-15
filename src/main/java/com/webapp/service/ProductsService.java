package com.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webapp.entity.Product;
import com.webapp.repo.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductsService {

	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	public void deleteProduct(int id) {
		productRepo.deleteById(id);
	}
	
	public Product getProductById(int id){
		return productRepo.findById(id);
	}
	
	
	public List<Product> getProductByCategoryId(int id){
		return productRepo.findAllByCategory_Id(id);
	}
}
