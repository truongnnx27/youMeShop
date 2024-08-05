package com.poly.entity;

import java.sql.Date;
import java.util.Optional;

import com.poly.repository.oderDetailRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @Column(name = "idOrderDetails", columnDefinition = "varchar(50)")
    String idOrderDetails;

    @Column(name = "quantity", columnDefinition = "int")
    @Builder.Default
    Integer quantity = 1;

    @Column(name = "price", columnDefinition = "float")
    double price;

    @Column(name = "amount")
    double amount;

    @Column(name = "color", columnDefinition = "nvarchar(50)")
    private String color;

    @Column(name = "size", columnDefinition = "nvarchar(50)")
    private String size;

    @Column(name = "paymentStatus", columnDefinition = "bit")
    boolean paymentStatus;

    @Column(name = "orderDate", columnDefinition = "date")
    Date orderDate;
    
    private boolean isPaid;

    // getters và setters
    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    
    public boolean isPaymentStatus() {
	    return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
	    this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatusDisplay() {
	    return paymentStatus ? "Đã thanh toán" : "Chưa thanh toán";
	}

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product products;
    
    public String getNameProduct()
    {
    	return products !=null ? products.getNameProduct() : null;
    }

    @ManyToOne
    @JoinColumn(name = "idusers")
    User user;
    //lấy thông tin của user
    public String getUserName() {
        return user != null ? user.getFullname() : null;
    }
    public String getAddress()
    {
    	return user !=null ? user.getAddress() : null;
    }
    
    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order order;
    
    public Double getAmountOrder()
    {
    	return order !=null ? order.getTotalPrice() : null;
    }
}
