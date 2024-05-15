package com.webapp.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapp.entity.User;
import com.webapp.global.GlobalData;
import com.webapp.repo.UserRepo;
import com.webapp.service.CategoryService;
import com.webapp.service.MailService;
import com.webapp.service.ProductsService;

@Controller
public class HomeController {

	@Autowired
	ProductsService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	MailService mailService;

	private String secretCode;
	
	private String emailToSendCode;

	@Autowired
	UserRepo userRepo;

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}

	@GetMapping("/shop")
	public String getAllCategories(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProducts());
		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String getCategories(@PathVariable int id, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getProductByCategoryId(id));
		return "shop";
	}

	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(@PathVariable int id, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("product", productService.getProductById(id));
		return "viewProduct";
	}

	@GetMapping("/forgotpassword")
	public String forgetPassword() {
		return "forgotpassword";
	}

	@PostMapping("/sendEmail")
	public String sendMailToUser(@RequestParam("email") String email , Model model) {
		emailToSendCode = email;
		String Secretcode = UUID.randomUUID().toString();
		mailService.sendMail(email,
				" This is the secret code to make a new password , Enter this code =  " + Secretcode,
				"Password Reset Request");
		model.addAttribute("secretCode", Secretcode);
		return "createNewPass";
	}

	@PostMapping("/createNewPass")
	public String createNewPass(@RequestParam("email") String email, @RequestParam("code") String code,
			@RequestParam("password") String password , Model model) {
		
		System.out.println(email + code + password);
		secretCode = (String) model.getAttribute("secretCode");
		if(emailToSendCode.equals(email)) {
			if (secretCode.equals(code)) {
				Optional<User> user1 = userRepo.findUserByEmail(email);
				if (user1.isPresent()) {
					User user = user1.get();
					user.setPassword(password);
					userRepo.save(user);
					return "successful";
				} else {
					  return "User is not present, please register first on the website";
				}
			} else {
				   return "Secret code is wrong, please put correct code";
			}
		}else {
			return "Email is wrong, please put correct email";
		}
	}
}
