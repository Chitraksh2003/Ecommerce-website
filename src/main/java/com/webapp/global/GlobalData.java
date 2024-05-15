package com.webapp.global;

import java.util.ArrayList;
import java.util.List;

import com.webapp.entity.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}
