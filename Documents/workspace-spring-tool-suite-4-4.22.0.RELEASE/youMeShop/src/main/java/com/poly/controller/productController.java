package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Product;
import com.poly.repository.ProductRepository;

@Controller
public class productController {
	
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping("/category/{id}")
	public String getProductByCategory(@PathVariable("id") String categoryId,Model model)
	{
		List<Product> productByCategoryId= productRepository.ListProductByCatalogue(categoryId);
		model.addAttribute("productByCategoryId",productByCategoryId);
		
		
		return "page/productByCategory";
	}

}
