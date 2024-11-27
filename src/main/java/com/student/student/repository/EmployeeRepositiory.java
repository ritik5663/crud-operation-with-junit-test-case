package com.student.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.student.model.Employee;
import com.student.student.model.User;

public interface EmployeeRepositiory extends JpaRepository<Employee,Long>{
	List<Employee> findByNameContainingIgnoreCase(String name);
	  
	  List<Employee> findByEmailContainingIgnoreCase(String email);

}
