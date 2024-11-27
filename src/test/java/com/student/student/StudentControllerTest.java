package com.student.student;

import com.student.student.controller.StudentController;
import com.student.student.model.Student;
import com.student.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentRepository studentRepository;

    public StudentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student("John Doe", 20, "john@example.com");
        Student student2 = new Student("Jane Smith", 22, "jane@example.com");
        List<Student> students = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(students);

        ResponseEntity<List<Student>> response = studentController.getAllStudents(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetStudentById() {
        long id = 1L;
        Student student = new Student("John Doe", 20, "john@example.com");
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        ResponseEntity<Student> response = studentController.getStudentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student("John Doe", 20, "john@example.com");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> response = studentController.createStudent(null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John ", response.getBody().getName());
    }

    @Test
    public void testUpdateStudent() {
        long id = 1L;
        Student existingStudent = new Student("John Doe", 20, "john@example.com");
        Student updatedStudent = new Student("Jane Doe", 21, "jane@example.com");
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        ResponseEntity<Student> response = studentController.updateStudent(id, updatedStudent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", response.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        long id = 1L;
        doNothing().when(studentRepository).deleteById(id);

        ResponseEntity<HttpStatus> response = studentController.deleteStudent(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteAllStudents() {
        doNothing().when(studentRepository).deleteAll();

        ResponseEntity<HttpStatus> response = studentController.deleteAllStudents();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentRepository, times(1)).deleteAll();
    }
}
