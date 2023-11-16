package com.damoyeo.healthyLife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	@GetMapping("/signup")
	public String signup() {
		return "index.html";
	}
	@GetMapping("/login")
	public String login() {
		return "index.html";
	}
	@GetMapping("/mypage")
	public String mypage() {
		return "index.html";
	}
	@GetMapping("/findId")
	public String findId() {
		return "index.html";
	}
	@GetMapping("/findPw")
	public String findPw() {
		return "index.html";
	}
	@GetMapping("/brand")
	public String brand() {
		return "index.html";
	}
	@GetMapping("/sportTest")
	public String sportTest() {
		return "index.html";
	}
	@GetMapping("/menuTest")
	public String menuTest() {
		return "index.html";
	}
	@GetMapping("/bmi")
	public String bmi() {
		return "index.html";
	}
	@GetMapping("/calorie")
	public String calorie() {
		return "index.html";
	}
	@GetMapping("/scheduler")
	public String scheduler() {
		return "index.html";
	}
	@GetMapping("/community")
	public String community() {
		return "index.html";
	}
	@GetMapping("/communityPost")
	public String communityPost() {
		return "index.html";
	}
	
	@GetMapping("/consulting")
	public String consulting() {
		return "index.html";
	}
	@GetMapping("/trainerMatch")
	public String trainerMatch() {
		return "index.html";
	}
	@GetMapping("/productForSale")
	public String productForSale() {
		return "index.html";
	}
	@GetMapping("/adminpage")
	public String adminpage() {
		return "index.html";
	}
	@GetMapping("/adminPage")
	public String adminPage() {
		return "index.html";
	}
	@GetMapping("/orderingPage")
	public String orderingPage() {
		return "index.html";
	}
	@GetMapping("/cartOrderingPage")
	public String cartOrderingPage() {
		return "index.html";
	}
	@GetMapping("/chat1")
	public String chat1() {
		return "index.html";
	}
	@GetMapping("/chat2")
	public String chat2() {
		return "index.html";
	}
	@GetMapping("/chat3")
	public String chat3() {
		return "index.html";
	}
	@GetMapping("/shop")
	public String shop() {
		return "index.html";
	}
	@GetMapping("/changePw")
	public String changePw() {
		return "index.html";
	}



}
