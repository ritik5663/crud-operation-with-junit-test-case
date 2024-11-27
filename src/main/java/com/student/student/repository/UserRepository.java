package com.student.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.student.model.Student;
import com.student.student.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	
	List<User> findByNameContainingIgnoreCase(String name);
	  
	  List<User> findByEmailContainingIgnoreCase(String email);
}
