package com.student.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.student.model.Student;
import com.student.student.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class StudentController {

  @Autowired
  StudentRepository studentRepository;

  @GetMapping("/students")
  public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String name) {
    try {
      List<Student> students = new ArrayList<>();

      if (name == null) {
        studentRepository.findAll().forEach(students::add);
      } else {
        studentRepository.findByNameContainingIgnoreCase(name).forEach(students::add);
      }

      if (students.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(students, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/students/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
    Optional<Student> studentData = studentRepository.findById(id);

    if (studentData.isPresent()) {
      return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/students")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    try {
      Student _student = studentRepository.save(new Student(student.getName(), student.getAge(), student.getEmail()));
      return new ResponseEntity<>(_student, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/students/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
    Optional<Student> studentData = studentRepository.findById(id);

    if (studentData.isPresent()) {
      Student _student = studentData.get();
      _student.setName(student.getName());
      _student.setAge(student.getAge());
      _student.setEmail(student.getEmail());
      return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/students/{id}")
  public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
    try {
      studentRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/students")
  public ResponseEntity<HttpStatus> deleteAllStudents() {
    try {
      studentRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
