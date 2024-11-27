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

import com.student.student.model.Student;
import com.student.student.model.User;
import com.student.student.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser(@RequestParam(required = false) String name) {
		try {
			List<User> users = new ArrayList<>();

			if (name == null) {
				userRepository.findAll().forEach(users::add);
			} else {
				userRepository.findByNameContainingIgnoreCase(name).forEach(users::add);
			}

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @GetMapping("/users/{id}")
	  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
	    Optional<User> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	      return new ResponseEntity<>(userData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	 @PostMapping("/users")
	  public ResponseEntity<User> createUser(@RequestBody User user) {
	    try {
	      User _user = userRepository.save(new User(user.getName(), user.getMobileNumber(), user.getAddress(),user.getEmail()));
	      return new ResponseEntity<>(_user, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @PutMapping("/users/{id}")
	  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
	    Optional<User> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	      User newUser = userData.get();
	      newUser.setName(user.getName());
	      newUser.setMobileNumber(user.getMobileNumber());
	      newUser.setAddress(user.getAddress());
	      newUser.setEmail(user.getEmail());
	      return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/users/{id}")
	  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
	    try {
	      userRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
 
	  @DeleteMapping("/users")
	  public ResponseEntity<HttpStatus> deleteAllUsers() {
	    try {
	      userRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
