package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.entity.Catalogue;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.service.CategoryService;
import com.poly.service.OrderDetailService;
import com.poly.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("view", "homePage :: homePage");
		
		List<Product> listProduct= productService.findAllProductsWithSizesAndColors();
		model.addAttribute("productList",listProduct);
		
		List<Catalogue> categoryList= categoryService.findAllCategory();
		model.addAttribute("categoryList",categoryList);
		
		
		
		return "page/homePage";
	}

}
