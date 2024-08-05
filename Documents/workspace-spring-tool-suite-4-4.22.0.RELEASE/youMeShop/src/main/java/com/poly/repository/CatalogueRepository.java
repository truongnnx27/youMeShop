package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Catalogue;


@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue,String>{

}
