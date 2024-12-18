package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.service.BorrowService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {
    private BorrowService borrowService;
    private BookDAO bookDAO;
    private StudentDAO studentDAO;

    private BorrowDAO borrowDAO;

    @BeforeEach
    void setUp() throws SQLException {
        bookDAO = new BookDAO(DbConnection.getConnection());
        studentDAO = new StudentDAO(DbConnection.getConnection());
        borrowDAO = new BorrowDAO();
        borrowService = new BorrowService(borrowDAO);

        // Ajouter un étudiant
        studentDAO.addStudent(new Student(1, "Alice"));
        studentDAO.addStudent(new Student(2, "Bob"));

        // Ajouter des livres
        bookDAO.add(new Book(1, "Java Programming", "John Doe", "aaa", 2010));
        bookDAO.add(new Book(2, "Advanced Java", "Jane Doe", "aaa", 2010));
    }

    @Test
    void testBorrowBook() {

        assertEquals("Livre emprunté avec succès!", borrowService.borrowBook(1, 5));
        assertFalse(bookDAO.getBookById(5).isAvailable());
    }

    @Test
    void testReturnBook() {
        borrowService.borrowBook(1,1);
        assertEquals("Livre retourné avec succès!", borrowService.returnBook(1, 1));
        assertTrue(bookDAO.getBookById(1).isAvailable());
    }

    @Test
    void testBorrowBookNotAvailable() {
        borrowService.borrowBook(1, 1);
        assertEquals("Le livre n'est pas disponible.", borrowService.borrowBook(2, 1));
    }

    @Test
    void testBorrowBookStudentNotFound() {
        assertEquals("Étudiant ou livre non trouvé.", borrowService.borrowBook(3, 1));
    }
}
