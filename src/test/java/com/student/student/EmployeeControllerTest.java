package com.student.student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.student.controller.EmployeeController;
import com.student.student.model.Employee;
import com.student.student.model.User;
import com.student.student.repository.EmployeeRepositiory;

public class EmployeeControllerTest {

	@Mock
	private EmployeeRepositiory employeeRepositiory;

	@InjectMocks
	private EmployeeController employeeController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetAllEmployees() throws Exception {
		Employee emp1 = new Employee("Ritik Sharma", "Java Engineer", 5555555.0, "IT", "ritik.pits@gmail.com");
		Employee emp2 = new Employee("Abhay Sharma", "Backend Engineer", 555215.0, "IT", "abhaysharma@gmail.com");

		when(employeeRepositiory.findAll()).thenReturn(Arrays.asList(emp1, emp2));

		mockMvc.perform(get("/api/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Ritik Sharma"))
				.andExpect(jsonPath("$[1].name").value("Abhay Sharma"));
	}

	@Test
	void testByEmployeeId() throws Exception {
		Employee emp = new Employee("Ritik Sharma", "Java Engineer", 5555555.0, "IT", "ritik.pits@gmail.com");

		when(employeeRepositiory.findById(1L)).thenReturn(Optional.of(emp));

		mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Ritik Sharma"))
				.andExpect(jsonPath("$.email").value("ritik.pits@gmail.com"));

	}

	@Test
	void testCreateEmployee() throws Exception {
		Employee emp = new Employee("Ritik Sharma", "Java Engineer", 5555555.0, "IT", "ritik.pits@gmail.com");

		when(employeeRepositiory.save(any(Employee.class))).thenReturn(emp);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(emp))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Ritik Sharma"))
				.andExpect(jsonPath("$.email").value("ritik.pits@gmail.com"));
	}

	@Test
	void testUpdateUser() throws Exception {
		Employee employee1 = new Employee("Ritik Sharma", "Java Engineer", 5555555.0, "IT", "ritik.pits@gmail.com");
		Employee employee2 = new Employee("Abhay Sharma", "Java Engineer", 555215.0, "IT", "ritik.pits@gmail.com");

		when(employeeRepositiory.findById(1L)).thenReturn(Optional.of(employee1));
		when(employeeRepositiory.save(any(Employee.class))).thenReturn(employee2);

		mockMvc.perform(put("/api/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Abhay Sharma"))
				.andExpect(jsonPath("$.email").value("ritik.pits@gmail.com"));
	}

	@Test
	void testDeleteEmployee() throws Exception {
		doNothing().when(employeeRepositiory).deleteById(1L);

		mockMvc.perform(delete("/api/employees/1")).andExpect(status().isNoContent());
	}

	@Test
	void testDeleteAllEmployee() throws Exception {
		doNothing().when(employeeRepositiory).deleteAll();

		mockMvc.perform(delete("/api/employees")).andExpect(status().isNoContent());
	}

}
