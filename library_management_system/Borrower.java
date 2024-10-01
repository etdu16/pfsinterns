package library_management;

public class Borrower {
	private int borrowerId;
	private String name;
	private String email;
	private String membershipDate;
	
	public Borrower(String a, String b, String c) {
		name=a;
		email=b;
		membershipDate=c;
	}
	
	public Borrower() {
	}

	public int getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMembershipDate() {
		return membershipDate;
	}

	public void setMembershipDate(String date) {
		this.membershipDate = date;
	}
}
