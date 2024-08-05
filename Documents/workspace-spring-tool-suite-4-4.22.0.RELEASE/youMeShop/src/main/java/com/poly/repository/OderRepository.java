package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Order;
import com.poly.entity.User;

@Repository
public interface OderRepository extends JpaRepository<Order,String> {
//	List<Order> findByUser(User user);
}
