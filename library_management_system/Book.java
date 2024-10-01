package library_management;
public class Book {
	
	private int bookId;
	private String title;
	private String author;
	private boolean isAvailable;
	
	public Book(String t, String a, boolean is) {
		title = t;
		author = a;
		isAvailable = is;
	}
	
	public Book() {
		//default
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public String toString() {
		return bookId+"-"+title+"-"+author+"-"+isAvailable;
	}

}
