package com.poly.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.entity.Color;
import com.poly.entity.Product;
import com.poly.entity.Size;

@Repository
public interface ProductRepository extends JpaRepository<Product,String>{
	
	@Query(value = "SELECT p FROM Product p WHERE p.catalogues.IdCatalogue =?1 ")
	List<Product>  ListProductByCatalogue(String CataID);
	
	
	@Query("SELECT p FROM Product p")
    List<Product> findAllProducts();

    @Query("SELECT s FROM Size s JOIN s.products p WHERE p.idProduct = :productId")
    List<Size> findSizesByProductId(@Param("productId") String productId);

    @Query("SELECT c FROM Color c JOIN c.products p WHERE p.idProduct = :productId")
    List<Color> findColorsByProductId(@Param("productId") String productId);
    
    
    //kiểm tra lấy id product cao nhất hiện tại
    @Query("SELECT MAX(CAST(SUBSTRING(p.idProduct, 8) AS long)) FROM Product p")
    Long findMaxId();
    
	
	
}
