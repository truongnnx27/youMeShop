package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

	@GetMapping("/category")
	public String category(Model model)
	{
		return "/page/category";
	}
}
