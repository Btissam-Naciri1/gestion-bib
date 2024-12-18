
package com.library.dao;

import com.library.model.Borrow;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    public BorrowDAO() {
    }

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        rs.getString("member"),
                        rs.getString("book"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

public void save(Borrow borrow) {
    // Code d'insertion SQL
}



    public void addBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (member, book, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, borrow.getStudent());
            stmt.setString(2, borrow.getBook());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Borrow findBorrowByStudentAndBook(int studentId, int bookId) {
        String query = "SELECT * FROM borrows WHERE member = ? AND book = ?";
        try (Connection connection = DbConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Borrow(
                            rs.getInt("id"),
                            rs.getString("student_name"),
                            rs.getString("book_title"),
                            rs.getDate("borrow_date"),
                            rs.getDate("return_date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBorrow(Borrow borrow) {
        String query = "UPDATE borrows SET return_date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.setInt(2, borrow.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
