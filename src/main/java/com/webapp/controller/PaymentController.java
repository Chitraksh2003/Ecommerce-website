
package com.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.webapp.Dto.BillingDetails;
import com.webapp.Dto.OrderRequest;
import com.webapp.Dto.OrderResponse;
import com.webapp.entity.Product;
import com.webapp.global.GlobalData;

@Controller
public class PaymentController {

	private RazorpayClient client;
	private static final String SECRET_ID = "your secret id";
	private static final String SECRET_KEY = "your secret key";

	@PostMapping("/createOrder")
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> data) throws Exception {
		OrderResponse orderResponse = new OrderResponse();
		String amount = (String) data.get("amount");
		double doubelAmount = Double.parseDouble(amount);
		int totalAmount = (int) doubelAmount;
		client = new RazorpayClient(SECRET_ID, SECRET_KEY);
		Order order = createRazorPayOrder(totalAmount);
		System.out.println("-----------------------------");
		String orderId = (String) order.get("id");
		System.out.println("Order_Id: " + orderId);
		System.out.println("-----------------------------");
		orderResponse.setRazorpayOrderId(orderId);
		orderResponse.setAmount(totalAmount);
		orderResponse.setSecretId(SECRET_ID);
		orderResponse.setSecretKey(SECRET_KEY);

		System.out.println(order.toString());
		return ResponseEntity.ok(order.toString());
	}

	private Order createRazorPayOrder(int amount2) throws RazorpayException {
		JSONObject options = new JSONObject();
		options.put("amount", amount2 * 100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		Order order = client.Orders.create(options);
		return order;
	}

	@PostMapping("/paymentDone")
	@ResponseBody
	public String paymentDone(@RequestBody Map<String, Object> data) {
		String razorpay_order_id = (String) data.get("razorpay_order_id");
		String razorpay_payment_id = (String) data.get("razorpay_payment_id");
		String razorpay_signature = (String) data.get("razorpay_signature");
		System.out.println(razorpay_order_id);
		System.out.println(razorpay_payment_id);
		System.out.println(razorpay_signature);
		return "redirect:/payNow";
	}

	@PostMapping(value = "/payNow", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String processPayment(@RequestBody BillingDetails billingDetails) {
		// Process the received billing details
		System.out.println("Received billing details: " + billingDetails);

		// Perform payment processing logic here

		// Return a response (optional)
		return "Payment successful"; // You can return any response as needed
	}



}
