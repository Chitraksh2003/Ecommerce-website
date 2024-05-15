package com.webapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findAllByCategory_Id(int id);
	
	Product findById(int id);
}
