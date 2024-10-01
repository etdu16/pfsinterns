package library_management;

import java.sql.*;
import java.util.*;

public class BookDAO {

	//use placeholders to prevent sql injection attacks
	public static void addBook(Book book) {
		String sql = "INSERT INTO Books (title, author, availability) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setBoolean(3, book.isAvailable());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteBook(Book book) {
		String sql = "DELETE FROM Books WHERE book_id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, book.getBookId());
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		String sql = "SELECT * FROM Books";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setAvailable(rs.getBoolean("availability"));
				books.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public static Book getBookById(int bookId) {
	    Book book = null;
	    String sql = "SELECT * FROM Books WHERE book_id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, bookId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            book = new Book();
	            book.setBookId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthor(rs.getString("author"));
	            book.setAvailable(rs.getBoolean("availability"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return book;
	}


}
