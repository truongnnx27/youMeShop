package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class register {
	
	@GetMapping("/register")
	public String registers()
	{
		return "page/dangki";
	}
}
