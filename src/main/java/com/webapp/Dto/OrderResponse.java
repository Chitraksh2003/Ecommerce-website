package com.webapp.Dto;

import lombok.Data;

@Data
public class OrderResponse {

	String secretKey;
	String razorpayOrderId;
	double amount;
	String secretId;
}
