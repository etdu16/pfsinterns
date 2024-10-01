package library_management;

import java.sql.*;
import java.util.*;

public class BorrowerDAO {

	//use placeholders to prevent sql injection attacks
	public static void addBorrower(Borrower borrower) {
		String sql = "INSERT INTO Borrowers (name, contact_info, membership_date) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, borrower.getName());
			stmt.setString(2, borrower.getEmail());
			stmt.setString(3, borrower.getMembershipDate());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteBorrower(Borrower borrower) {
	    String sql = "DELETE FROM Borrowers WHERE borrower_id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, borrower.getBorrowerId());
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected == 0) {
	            System.out.println("No borrower found with the given ID.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static ArrayList<Borrower> getAllBorrowers() {
	    ArrayList<Borrower> borrowers = new ArrayList<>();
	    String sql = "SELECT * FROM Borrowers";  // Ensure the table name is correct
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            Borrower borrower = new Borrower();
	            borrower.setBorrowerId(rs.getInt("borrower_id"));  // Set the borrower ID
	            borrower.setName(rs.getString("name"));
	            borrower.setEmail(rs.getString("contact_info"));
	            borrower.setMembershipDate(rs.getString("membership_date"));
	            borrowers.add(borrower);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return borrowers;
	}

	
	public static Borrower getBorrowerById(int borrowerId) {
	    Borrower borrower = null;
	    String sql = "SELECT * FROM Borrowers WHERE borrower_id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, borrowerId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            borrower = new Borrower();
	            borrower.setBorrowerId(rs.getInt("borrower_id"));
	            borrower.setName(rs.getString("name"));
	            borrower.setEmail(rs.getString("contact_info"));
	            borrower.setMembershipDate(rs.getString("membership_date"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return borrower;
	}


}
