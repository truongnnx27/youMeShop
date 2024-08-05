package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.poly.entity.User;
import com.poly.repository.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	UsersRepository userDAO;
	
	public User findByEmail(String email)
	{
		return userDAO.findByEmail(email);
	}
	
	public List<User> findAll()
	{
		return userDAO.findAll();
	}
	
	public User save(User user)
	{
		return userDAO.save(user);
	}
	
	public void deleteById(String idUser)
	{
		userDAO.deleteById(idUser);
	}
	
	public User findById(String idUser)
	{
		return userDAO.findById(idUser).orElse(null);
	}
	
	public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDAO.findByFullname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
