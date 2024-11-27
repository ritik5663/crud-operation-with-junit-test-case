package com.student.student.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.student.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
  
  List<Student> findByNameContainingIgnoreCase(String name);
  
  List<Student> findByEmailContainingIgnoreCase(String email);
}
