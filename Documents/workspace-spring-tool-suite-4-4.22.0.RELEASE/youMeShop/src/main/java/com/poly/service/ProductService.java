package com.poly.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.poly.entity.Catalogue;
import com.poly.entity.Color;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.Size;
import com.poly.entity.User;
import com.poly.repository.CatalogueRepository;
import com.poly.repository.ColorRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.SizeRepository;
import com.poly.repository.UsersRepository;
import com.poly.repository.oderDetailRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private CatalogueRepository catalogueRepository;
    
    @Autowired 
    private UsersRepository usersRepository;

    @Autowired
    private oderDetailRepository detailRepository;
    
    
    public List<Size> saveSizes(List<Size> sizes) {
        return sizes.stream()
                    .map(size -> {
                        if (size.getIdSize() == null || size.getIdSize().isEmpty()) {
                            size.prePersist(); // Tạo idSize duy nhất nếu chưa có
                        }
                        return sizeRepository.save(size);
                    })
                    .collect(Collectors.toList());
    }

    public List<Color> saveColors(List<Color> colors) {
        return colors.stream()
                     .map(color -> {
                         if (color.getIdColor() == null || color.getIdColor().isEmpty()) {
                             color.prePersist(); // Tạo idColor duy nhất nếu chưa có
                         }
                         return colorRepository.save(color);
                     })
                     .collect(Collectors.toList());
    }

    //add new product
    public Product addProduct(Product product, List<Size> sizes, List<Color> colors) {
        Optional<Catalogue> catalogueOpt = catalogueRepository.findById(product.getCatalogues().getIdCatalogue());

        if (!catalogueOpt.isPresent()) {
            throw new RuntimeException("Catalogue ID không tồn tại: " + product.getCatalogues().getIdCatalogue());
        }

        List<Size> finalSizes = saveSizes(sizes);
        List<Color> finalColors = saveColors(colors);

        product.setSizes(finalSizes);
        product.setColors(finalColors);

     // Tạo idProduct duy nhất nếu chưa có
        if (product.getIdProduct() == null || product.getIdProduct().isEmpty()) {
            Long maxId = productRepository.findMaxId();
            if (maxId == null) {
                maxId = 0L;
            }
            product.setIdProduct("product" + (maxId + 1));
        }

        productRepository.save(product);
        return product;
    }
    
    
    //hiển thị sản phẩm
    public List<Product> findAllProductsWithSizesAndColors() {
        List<Product> products = productRepository.findAllProducts();
        products.forEach(product -> {
            List<Size> sizes = productRepository.findSizesByProductId(product.getIdProduct());
            List<Color> colors = productRepository.findColorsByProductId(product.getIdProduct());
            product.setSizes(sizes);
            product.setColors(colors);
        });
        return products;
    }
    
    
    public void deleteById(String id)
    {
    	productRepository.deleteById(id);
    }
    
    
    //lọc sản phẩm thuộc 1 danh mục
    public List<Product> listProductByCategory(String categoryId)
    {
    	return productRepository.ListProductByCatalogue(categoryId);
    }
    
    //lấy id product để hiển thị productDetail
    public Product findProductById(String id)
    {
    	return productRepository.findById(id).orElse(null);
    }
    
    public List<Product> findAll()
    {
    	return productRepository.findAll();
    }

	public long countTotalProducts() {
		return productRepository.count();
	}


	
}
