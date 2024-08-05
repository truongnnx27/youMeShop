package com.poly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, String>{
	List<Color> findByProducts_IdProduct(String idProduct);

	Optional<Color> findByNameColor(String nameColor);

}
