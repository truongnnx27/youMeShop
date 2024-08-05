package com.poly.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Orders")  // Đổi tên bảng thành Orders
public class Order {	
	
	@Id
	@Column(name = "idOrder", columnDefinition = "varchar(50)")
	String idOrder;
	
	@Column(name = "orderDate", columnDefinition = "date")
	Date orderDate;
	
	@Column(name = "paymentStatus", columnDefinition = "bit")
	boolean paymentStatus;
	
	@Column(name = "totalPrice", columnDefinition = "float")
	double totalPrice;
	
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
	
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

}