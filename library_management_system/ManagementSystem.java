package library_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagementSystem {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Books", createBookPanel());
        tabbedPane.addTab("Borrowers", createBorrowerPanel());
        tabbedPane.addTab("Transactions", createTransactionPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JPanel createBookPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(10, 20, 100, 25);
        panel.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(120, 20, 200, 25);
        panel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(10, 50, 100, 25);
        panel.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(120, 50, 200, 25);
        panel.add(authorField);

        JButton addButton = new JButton("Add Book");
        addButton.setBounds(10, 80, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                Book book = new Book(title, author, true);
                BookDAO.addBook(book);
                JOptionPane.showMessageDialog(null, "Book added successfully!");
            }
        });

        JButton viewButton = new JButton("View Books");
        viewButton.setBounds(170, 80, 150, 25);
        panel.add(viewButton);

        String[] columnNames = {"Book ID", "Title", "Author", "Availability"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBounds(10, 120, 360, 200);
        panel.add(scrollPane);

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);

                ArrayList<Book> books = BookDAO.getAllBooks();
                for (Book book : books) {
                    Object[] row = {book.getBookId(), book.getTitle(), book.getAuthor(), book.isAvailable()};
                    tableModel.addRow(row);
                }
            }
        });

        JLabel deleteLabel = new JLabel("Book ID to Delete:");
        deleteLabel.setBounds(10, 330, 150, 25);
        panel.add(deleteLabel);

        JTextField deleteField = new JTextField();
        deleteField.setBounds(150, 330, 100, 25);
        panel.add(deleteField);

        JButton deleteButton = new JButton("Delete Book");
        deleteButton.setBounds(260, 330, 110, 25);
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookIdText = deleteField.getText();
                try {
                    int bookId = Integer.parseInt(bookIdText);
                    Book bookToDelete = new Book();
                    bookToDelete.setBookId(bookId);
                    BookDAO.deleteBook(bookToDelete);
                    JOptionPane.showMessageDialog(null, "Book deleted successfully!");
                    deleteField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Book ID. Please enter a valid integer.");
                }
            }
        });

        return panel;
    }



    private static JPanel createBorrowerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 100, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 20, 200, 25);
        panel.add(nameField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(10, 50, 100, 25);
        panel.add(contactLabel);

        JTextField contactField = new JTextField();
        contactField.setBounds(120, 50, 200, 25);
        panel.add(contactField);

        JButton addButton = new JButton("Add Borrower");
        addButton.setBounds(10, 80, 150, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both name and contact information.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Borrower borrower = new Borrower(name, contact, "2024-09-30");
                try {
                    BorrowerDAO.addBorrower(borrower);
                    JOptionPane.showMessageDialog(null, "Borrower added successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding borrower: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton viewButton = new JButton("View Borrowers");
        viewButton.setBounds(170, 80, 150, 25);
        panel.add(viewButton);

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Borrower> borrowers = BorrowerDAO.getAllBorrowers();
                String[] columnNames = {"Borrower ID", "Name", "Contact", "Membership Date"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                for (Borrower b : borrowers) {
                    Object[] row = {b.getBorrowerId(), b.getName(), b.getEmail(), b.getMembershipDate()};
                    model.addRow(row);
                }

                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null, scrollPane, "Borrower Records", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JLabel deleteLabel = new JLabel("Borrower ID:");
        deleteLabel.setBounds(10, 120, 100, 25);
        panel.add(deleteLabel);

        JTextField deleteField = new JTextField();
        deleteField.setBounds(120, 120, 200, 25);
        panel.add(deleteField);

        JButton deleteButton = new JButton("Delete Borrower");
        deleteButton.setBounds(10, 150, 150, 25);
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String borrowerIdStr = deleteField.getText().trim();
                if (borrowerIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a Borrower ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int borrowerId = Integer.parseInt(borrowerIdStr);
                    Borrower borrower = new Borrower();
                    borrower.setBorrowerId(borrowerId);
                    BorrowerDAO.deleteBorrower(borrower);
                    JOptionPane.showMessageDialog(null, "Borrower deleted successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Borrower ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting borrower: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private static JPanel createTransactionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel borrowerIdLabel = new JLabel("Borrower ID:");
        borrowerIdLabel.setBounds(10, 20, 100, 25);
        panel.add(borrowerIdLabel);

        JTextField borrowerIdField = new JTextField();
        borrowerIdField.setBounds(120, 20, 200, 25);
        panel.add(borrowerIdField);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(10, 50, 100, 25);
        panel.add(bookIdLabel);

        JTextField bookIdField = new JTextField();
        bookIdField.setBounds(120, 50, 200, 25);
        panel.add(bookIdField);

        JLabel dateBorrowedLabel = new JLabel("Date Borrowed (YYYY-MM-DD):");
        dateBorrowedLabel.setBounds(10, 80, 200, 25);
        panel.add(dateBorrowedLabel);

        JButton addTransactionButton = new JButton("Add Transaction");
        addTransactionButton.setBounds(10, 110, 150, 25);
        panel.add(addTransactionButton);

        addTransactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int borrowerId = Integer.parseInt(borrowerIdField.getText());
                    int bookId = Integer.parseInt(bookIdField.getText());
                    
                    Borrower borrower = BorrowerDAO.getBorrowerById(borrowerId);
                    Book book = BookDAO.getBookById(bookId);

                    TransactionDAO.addTransaction(borrower, book);
                    JOptionPane.showMessageDialog(null, "Transaction added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid IDs.");
                }
            }
        });

        JButton viewTransactionsButton = new JButton("View Transactions");
        viewTransactionsButton.setBounds(170, 110, 200, 25);
        panel.add(viewTransactionsButton);

        String[] transactionColumnNames = {"Transaction ID", "Borrower ID", "Book ID", "Date Borrowed"};
        DefaultTableModel transactionTableModel = new DefaultTableModel(transactionColumnNames, 0);
        JTable transactionTable = new JTable(transactionTableModel);
        JScrollPane transactionScrollPane = new JScrollPane(transactionTable);
        transactionScrollPane.setBounds(10, 150, 360, 200); // Set position and size of the scroll pane
        panel.add(transactionScrollPane);

        viewTransactionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transactionTableModel.setRowCount(0);

                ArrayList<Transaction> transactions = TransactionDAO.getTransactionRecords();
                for (Transaction transaction : transactions) {
                    Object[] row = {
                        transaction.getTransaction_id(),
                        transaction.getBorrower_id(),
                        transaction.getBook_id(),
                        transaction.getBorrow_date()
                    };
                    transactionTableModel.addRow(row);
                }
            }
        });

        JLabel deleteTransactionLabel = new JLabel("Transaction ID to Delete:");
        deleteTransactionLabel.setBounds(10, 360, 200, 25);
        panel.add(deleteTransactionLabel);

        JTextField deleteTransactionField = new JTextField();
        deleteTransactionField.setBounds(220, 360, 100, 25);
        panel.add(deleteTransactionField);

        JButton deleteTransactionButton = new JButton("Delete Transaction");
        deleteTransactionButton.setBounds(330, 360, 150, 25);
        panel.add(deleteTransactionButton);

        deleteTransactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionId = Integer.parseInt(deleteTransactionField.getText());
                    TransactionDAO.deleteTransaction(transactionId);
                    JOptionPane.showMessageDialog(null, "Transaction deleted successfully!");
                    deleteTransactionField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Transaction ID. Please enter a valid integer.");
                }
            }
        });

        return panel;
    }

}
