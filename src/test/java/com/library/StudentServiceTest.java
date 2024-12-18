package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() throws SQLException {
        studentDAO = new StudentDAO(DbConnection.getConnection());
        studentService = new StudentService(studentDAO);
    }

    @Test
    void testAddStudent() {
        Student student = new Student(1,"Alice");
        studentService.addStudent(student);
        assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("Alice", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testUpdateStudent() {
        Student student1 = new Student(1,"Alice");
        Student student2 = new Student(1, "Alice Smith");
        studentService.addStudent(student1);
        studentService.updateStudent(student2);
        assertEquals("Alice Smith", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testDeleteStudent() {
        //Student student1 = new Student(1,"Alice");
        //studentService.addStudent(student1);
        studentService.deleteStudent(2);
        assertTrue(studentDAO.getStudentById(2).isEmpty());
    }

    @Test
    void testGetAllStudents() {
        //Student student1 = new Student(1,"Alice");
        //Student student2 = new Student(2,"Bob");
        //studentService.addStudent(student1);
        //studentService.addStudent(student2);
        assertEquals(12, studentDAO.getAllStudents().size());
    }
}
