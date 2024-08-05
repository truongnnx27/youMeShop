package com.poly.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
		@Id
		@Column(name = "idProduct",columnDefinition = "varchar(50)")
		String idProduct;
		
		@Column(name = "nameProduct",columnDefinition = "nvarchar(100)")
		String nameProduct;
		
		@Column(name = "price",columnDefinition = "float")
		double Price;
		
		@Column(name = "describe",columnDefinition = "nvarchar(200)")
		String describe;
		
		
		@Column(name = "imageUrl",columnDefinition = "nvarchar(200)")
		String imageUrl;
		
		@Column(name = "quantity",columnDefinition = "int")
		Integer quantity;
		
		
		@ManyToOne
		@JoinColumn(name = "id_catalogue")
		Catalogue catalogues;
		
		@OneToMany(mappedBy = "products",cascade = CascadeType.ALL)
		List<OrderDetail> orderDetails;
		
		@ManyToMany
		@JoinTable(
				name = "Product_Color",
				joinColumns = @JoinColumn(name = "id_product"),
				inverseJoinColumns = @JoinColumn(name = "id_color")
				)
		List<Color>colors;
		
		@ManyToMany
		@JoinTable(
				name = "Product_Size",
				joinColumns = @JoinColumn(name = "id_product"),
				inverseJoinColumns = @JoinColumn(name = "id_size")
				)
		List<Size>sizes;
}
