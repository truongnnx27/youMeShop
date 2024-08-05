package com.poly.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Catalogue")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Catalogue {
	@Id
	@Column(name = "id_catalogue",columnDefinition = "varchar(50)")
	String IdCatalogue;
	
	@Column(name = "name_catalogue",columnDefinition = "nvarchar(100)")
	String nameCatalogue;
	
	@Column(name = "quantity",columnDefinition = "int")
	Integer quantity;
	
	@Column(name = "image",columnDefinition = "nvarchar(200)")
	String image;
	
	@OneToMany(mappedBy = "catalogues")
	List<Product> products;
}
