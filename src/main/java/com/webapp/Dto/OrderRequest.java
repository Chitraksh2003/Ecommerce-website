package com.webapp.Dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class OrderRequest {

	String customerName;
	String email;
	String phoneNumber;
	BigInteger amount;
}
