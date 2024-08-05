package com.poly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {

//    @Query("SELECT u FROM User u WHERE u.idUser like 'USER%' ORDER BY u.idUser DESC LIMIT 1")
//    User findLastIdClient();

	User findByEmail(String email);
	Optional<User> findByFullname(String fullname);
	//Optional<User> findByIdUser(String idUser); 
}