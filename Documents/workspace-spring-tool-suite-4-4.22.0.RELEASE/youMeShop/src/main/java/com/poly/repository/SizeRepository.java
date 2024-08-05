package com.poly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, String>{
	
	List<Size> findByProducts_IdProduct(String idProduct);

	Optional<Size> findByNameSize(String nameSize);
	
	

}
