package com.webapp.Dto;

import lombok.Data;

@Data

public class BillingDetails {

	   private String firstName;
	    private String lastName;
	    private String addressLine1;
	    private String addressLine2;
	    private String postcode;
	    private String city;
	    private String phone;
	    private String email;
	    private String additionalInfo;
	    private Double totalAmount;

}
