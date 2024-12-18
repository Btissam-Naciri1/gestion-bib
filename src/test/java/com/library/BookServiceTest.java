package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() throws SQLException {
        bookDAO = new BookDAO(DbConnection.getConnection());
        bookService = new BookService(bookDAO);
    }

    @Test
    void testAddBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "aaaaaaaa",2010);
        bookService.addBook(book);
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookDAO.getBookById(1).getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(19, "Java Programming", "John Doe", "aaa", 2010);
        bookService.addBook(book);
        // Ensure initial book is available
        book.setAvailable(true);

        Book updatedBook = new Book(19, "Advanced Java", "Jane Doe", "aaa", 2010);
        updatedBook.setAvailable(false); // Ensure this is explicitly set
        bookService.updateBook(updatedBook);

        Book fetchedBook = bookDAO.getBookById(19);
        assertEquals("Advanced Java", fetchedBook.getTitle());
        assertFalse(fetchedBook.isAvailable()); // Confirm the change
    }



    @Test
    void testDeleteBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "aaa", 2010);
        bookService.addBook(book);
        bookService.deleteBook(3);
        assertTrue(bookDAO.getBookById(2).isEmpty());
    }
}
