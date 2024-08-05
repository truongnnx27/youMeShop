package com.poly.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.poly.DTO.CategoryStatsDTO;
import com.poly.entity.Catalogue;
import com.poly.entity.Color;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.Size;
import com.poly.repository.oderDetailRepository;
import com.poly.service.CategoryService;
import com.poly.service.OrderDetailService;
import com.poly.service.ProductService;
import com.poly.util.FileUploadUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private oderDetailRepository orderDetailRepository;

    @GetMapping("/form")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());

        List<Catalogue> categoryList = categoryService.findAllCategory();
        model.addAttribute("categoryList", categoryList);
        
        List<Product> productList = productService.findAllProductsWithSizesAndColors();
        model.addAttribute("productList",productList);
        
        List<OrderDetail> listOrderDetai = orderDetailService.getAllOrderDetails();
        model.addAttribute("listOrderDetail",listOrderDetai);
        
        long totalProduct = productService.countTotalProducts();
        model.addAttribute("totalProduct", totalProduct);
        
        long totalOrderDetails = orderDetailService.countTotalOrderDetails();
        model.addAttribute("totalOrderDetails",totalOrderDetails);
        
        return "admin/admin";
    }

    @PostMapping("/add")
    public String addProduct(
        @ModelAttribute Product product,
        @RequestParam String idCatalogue,
        @RequestParam String sizeNames,
        @RequestParam String colorNames,
        @RequestParam("productImage") MultipartFile productImage,
        Model model) {

        Catalogue catalogue = new Catalogue();
        catalogue.setIdCatalogue(idCatalogue);
        product.setCatalogues(catalogue);
        
        //kiểm tra và lưu file upload
        if(!productImage.isEmpty())
        {
        	try {
				//đường dẫn lưu trữ file
        		String fileName= productImage.getOriginalFilename();
        		String uploadDir= "";
        		
        		//tạo thư mục nếu chưa có
        		FileUploadUtil.saveFile(uploadDir, fileName, productImage);
        		
        		//thiết lập đường dẫn
        		product.setImageUrl(uploadDir + fileName);
        		
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("message","tải ảnh không thành công!");
				return "admin/admin";
			}
        }
        

        List<Size> sizes = Arrays.stream(sizeNames.split(","))
                                 .map(name -> new Size(null, name.trim(), null))
                                 .collect(Collectors.toList());

        List<Color> colors = Arrays.stream(colorNames.split(","))
                                   .map(name -> new Color(null, name.trim(), null))
                                   .collect(Collectors.toList());

        try {
            productService.addProduct(product, sizes, colors);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/admin";
        }

        return "redirect:/admin/form";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id)
    {
    	productService.deleteById(id);
    	return "redirect:/admin/form";
    }
    
    
    //api thống kê sản phẩm theo danh mục
    @GetMapping("/category-stats")
    @ResponseBody
    public List<CategoryStatsDTO> getCategoryStats() {
        return categoryService.findAllCategory().stream()
            .map(catalogue -> new CategoryStatsDTO(
                catalogue.getNameCatalogue(),
                catalogue.getProducts().size()
            ))
            .collect(Collectors.toList());
    }
    
    //hiển thị chi tiết order
    @GetMapping("/orderDetail/{id}")
    public String getOrderDetail(@PathVariable("id") String idOrderDetails, Model model) {
        Optional<OrderDetail> orderDetailOpt = orderDetailRepository.findById(idOrderDetails);
        if (orderDetailOpt.isPresent()) {
            model.addAttribute("orderDetail", orderDetailOpt.get());
            return "admin/orderDetails"; // Tên của trang chi tiết đơn hàng
        } else {
            return "redirect:/admin/form"; // Hoặc trang lỗi nếu không tìm thấy đơn hàng
        }
    }
}
