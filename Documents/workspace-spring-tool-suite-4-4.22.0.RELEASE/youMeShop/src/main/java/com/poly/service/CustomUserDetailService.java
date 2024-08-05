package com.poly.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poly.entity.CustomUserDetails;
import com.poly.entity.User;


@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User account = userService.findByEmail(email);
		if (account== null) {
			throw new UsernameNotFoundException("User not found");
		}
		else
		{
			Collection<GrantedAuthority> authorities = new HashSet<>();
			if (account.getRole().equals("ADMIN")) {
				authorities.add(new SimpleGrantedAuthority("ADMIN"));
			}
			else
			{
				authorities.add(new SimpleGrantedAuthority("USER"));
			}
			return new CustomUserDetails(authorities,account.getIdUser(),account.getEmail(),account.getPassword(),account.getFullname(),account.getStatus(),true,true,true);
		}
	}
	
	
	

}
