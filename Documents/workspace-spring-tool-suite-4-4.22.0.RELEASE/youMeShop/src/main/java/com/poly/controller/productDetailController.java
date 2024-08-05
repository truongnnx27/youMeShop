package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Color;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.Size;
import com.poly.service.OrderDetailService;
import com.poly.service.ProductService;

@Controller
public class productDetailController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
    private OrderDetailService orderDetailService;
	
	@RequestMapping("/DetailProduct/{id}")
	public String productDetails(Model model,@PathVariable("id") String id )
	{
		List<Product> productList= productService.findAll();
		model.addAttribute("listProduct",productList);
		
		Product product= productService.findProductById(id);
		model.addAttribute("detailProd",product);
		
		List<Color> colors = product.getColors();
		model.addAttribute("colors", colors);

		List<Size> sizes = product.getSizes();
		model.addAttribute("sizes", sizes);
		
		List<OrderDetail> cart = orderDetailService.getCartForCurrentUser();
		// Tính tổng số lượng sản phẩm có trong giỏ hàng
        int totalQuantity = cart.stream().mapToInt(OrderDetail::getQuantity).sum();
        model.addAttribute("totalQuantity", totalQuantity);
		
		return "page/productDetail";
	}

}
