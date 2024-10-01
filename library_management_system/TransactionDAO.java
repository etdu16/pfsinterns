package library_management;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {

    public static void addTransaction(Borrower borrower, Book book) {
        String sql = "INSERT INTO Transactions (borrower_id, book_id, borrow_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrower.getBorrowerId());
            stmt.setInt(2, book.getBookId());
            stmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTransaction(int transaction_id) {
        String sql = "DELETE FROM Transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction_id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaction> getTransactionRecords() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransaction_id(rs.getInt("transaction_id"));
                transaction.setBook_id(rs.getInt("book_id"));
                transaction.setBorrower_id(rs.getInt("borrower_id"));
                transaction.setBorrow_date(rs.getDate("borrow_date").toString());
                transaction.setReturn_date(rs.getDate("return_date")==null ? "none":rs.getDate("return_date").toString());

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static Transaction getTransactionById(int transactionId) {
        Transaction transaction = null;
        String sql = "SELECT * FROM Transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setTransaction_id(rs.getInt("transaction_id"));
                transaction.setBook_id(rs.getInt("book_id"));
                transaction.setBorrower_id(rs.getInt("borrower_id"));
                transaction.setBorrow_date(rs.getDate("borrow_date").toString());
                transaction.setReturn_date(rs.getDate("return_date").toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
