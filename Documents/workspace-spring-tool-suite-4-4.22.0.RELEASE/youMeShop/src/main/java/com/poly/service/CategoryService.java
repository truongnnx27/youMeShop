package com.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Catalogue;
import com.poly.repository.CatalogueRepository;

@Service
public class CategoryService {
	
	@Autowired
	CatalogueRepository catalogueRepository;
	
	
	public List<Catalogue> findAllCategory()
	{
		return catalogueRepository.findAll(); 
	}
	
	public Catalogue createCategory(Catalogue catalogue)
	{
		return catalogueRepository.save(catalogue);
	}

	public Catalogue updateCategory(Catalogue catalogue)
	{
		return catalogueRepository.save(catalogue);
	}
	
	public void deleteCategory(String idCatalogue)
	{
		catalogueRepository.findById(idCatalogue);
	}
}
