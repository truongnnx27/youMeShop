package com.poly.repository;



import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.User;


@Repository
public interface oderDetailRepository extends JpaRepository<OrderDetail, String> {

	Optional<OrderDetail> findByProducts(Product products);
	List<OrderDetail> findByUser(User user);
    
    //OrderDetail findByProductsAndColorAndSize(User user,Product products, String color, String size);
    
	//lấy id cao nhất
    @Query("SELECT MAX(CAST(SUBSTRING(o.idOrderDetails, 6, LENGTH(o.idOrderDetails)) AS long)) FROM OrderDetail o")
    Long findMaxId();
    
	List<OrderDetail> findByUserAndProductsAndColorAndSize(User user, Product product, String color, String size);
	Collection<OrderDetail> findByOrder(Order order);
	List<OrderDetail> findByUserAndIsPaid(User user, boolean isPaid);
	List<OrderDetail> findByUserAndProductsAndColorAndSizeAndIsPaid(User user, Product product, String color,String size, boolean b); 
	
}
    
	

