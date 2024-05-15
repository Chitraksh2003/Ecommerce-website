package com.webapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private int id;

	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "category_id",referencedColumnName = "category_id")
	private Category category;
	
	private double price;
	
	private double weight;
	
	private String description;
	
	private String imageName;
}
