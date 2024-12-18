
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.util.Date;

public class BorrowService {

    private BorrowDAO borrowDAO;
    private StudentDAO studentDAO;
    private BookDAO bookDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    // Méthode pour emprunter un livre
    public String borrowBook(int studentId, int bookId) {
        // Retrieve the student and book using their IDs
        Student student = studentDAO.getStudentById(studentId);
        Book book = bookDAO.getBookById(bookId);

        if (student == null || book == null) {
            return "Étudiant ou livre non trouvé.";
        }

        if (!book.isAvailable()) {
            return "Le livre n'est pas disponible.";
        }

        // Mark the book as borrowed
        book.isAvailable();

        // Create and save the Borrow record
        Borrow borrow = new Borrow(student.getName(), book.getTitle());
        borrowDAO.save(borrow);

        return "Livre emprunté avec succès!";
    }


    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        System.out.println("Liste des emprunts...");
        // Afficher les emprunts enregistrés (adapté selon votre DAO)
    }

    public String returnBook(int studentId, int bookId) {
        // Récupérer le livre et l'étudiant à partir de leurs IDs
        Student student = studentDAO.getStudentById(studentId);
        Book book = bookDAO.getBookById(bookId);

        if (student == null || book == null) {
            return "Étudiant ou livre non trouvé.";
        }

        // Vérifier si le livre a été emprunté
        Borrow borrow = borrowDAO.findBorrowByStudentAndBook(studentId, bookId);
        if (borrow == null) {
            return "Aucun emprunt trouvé pour cet étudiant et ce livre.";
        }

        // Marquer le livre comme disponible
        book.isAvailable();
        bookDAO.update(book);

        // Mettre à jour la date de retour de l'emprunt
        borrow.setReturnDate(new Date());
        borrowDAO.updateBorrow(borrow);

        return "Livre retourné avec succès!";
    }

}
