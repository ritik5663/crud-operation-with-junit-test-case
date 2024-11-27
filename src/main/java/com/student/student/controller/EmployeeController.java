package com.student.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.student.model.Employee; // Assume the Employee model exists
import com.student.student.repository.EmployeeRepositiory;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepositiory employeeRepository;

    // Get all employees or search by name
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String name) {
        try {
            List<Employee> employees = new ArrayList<>();

            if (name == null) {
                employeeRepository.findAll().forEach(employees::add);
            } else {
                employeeRepository.findByNameContainingIgnoreCase(name).forEach(employees::add);
            }

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get employee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee _employee = employeeRepository.save(new Employee(employee.getName(), employee.getJobTitle(), employee.getSalary(), employee.getDepartment(),employee.getEmail()));
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            Employee updatedEmployee = employeeData.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setJobTitle(employee.getJobTitle());
            updatedEmployee.setSalary(employee.getSalary());
            updatedEmployee.setDepartment(employee.getDepartment());
            updatedEmployee.setEmail(employee.getEmail());
            return new ResponseEntity<>(employeeRepository.save(updatedEmployee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an employee by ID
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") long id) {
        try {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all employees
    @DeleteMapping("/employees")
    public ResponseEntity<HttpStatus> deleteAllEmployees() {
        try {
            employeeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
