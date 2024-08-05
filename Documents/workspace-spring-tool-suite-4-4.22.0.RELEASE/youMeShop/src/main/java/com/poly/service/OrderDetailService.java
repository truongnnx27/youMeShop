package com.poly.service;

import com.poly.entity.OrderDetail;
import com.poly.entity.User;
import com.poly.repository.UsersRepository;
import com.poly.repository.oderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private oderDetailRepository orderDetailRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public void removeOrderDetail(String id) {
        orderDetailRepository.deleteById(id);
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
    
    public synchronized String generateOrderId() {
        Long maxId = orderDetailRepository.findMaxId();
        if (maxId == null) {
            maxId = 0L;
        }
        return "order" + (maxId + 1);
    }
    
    public OrderDetail findById(String id)
	{
		return orderDetailRepository.findById(id).get();
	}
    
    public List<OrderDetail> getCartForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = usersRepository.findByFullname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderDetailRepository.findByUserAndIsPaid(user, false);
    }
    
    public long countTotalOrderDetails()
    {
    	return orderDetailRepository.count();
    }
    
}
