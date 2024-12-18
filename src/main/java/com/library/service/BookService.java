package com.library.service;
import com.library.dao.BookDAO; // Importation de BookDAO
import com.library.dao.StudentDAO;
import com.library.model.Book;   // Importation de Book
import com.library.util.DbConnection;

import java.sql.SQLException;
import java.util.List;


public class BookService {
    private BookDAO bookDAO;  // Utilisation de DAO pour la gestion des livres

    // Constructeur qui initialise l'objet BookDAO
   public BookService() throws SQLException {
        this.bookDAO = new BookDAO(DbConnection.getConnection());
    }
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    // Ajouter un livre
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    // Afficher tous les livres
    public void displayBooks() {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Trouver un livre par ID
    public Book findBookById(int id) {
        return bookDAO.getBookById(id);
    }

    // Supprimer un livre par ID
    public void deleteBook(int id) {
        bookDAO.delete(id);
    }

    // Mise à jour des informations d'un livre
    public void updateBook(Book book) {
        bookDAO.update(book);
    }

}
