import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.MessagingException;

public class BasicEmailClient extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField recipientField;
    private JTextField subjectField;
    private JTextArea messageArea;
    private EmailSender emailSender;

    public BasicEmailClient() {
        setTitle("Basic Email Client");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        emailSender = new EmailSender();

        JPanel credentialsPanel = new JPanel(new GridLayout(3, 2));
        credentialsPanel.add(new JLabel("Your Email:"));
        emailField = new JTextField();
        credentialsPanel.add(emailField);
        credentialsPanel.add(new JLabel("App Password:")); //alfldwyuzsvofzfq
        passwordField = new JPasswordField();
        credentialsPanel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        credentialsPanel.add(loginButton);
        
        add(credentialsPanel, BorderLayout.NORTH);

        JPanel emailPanel = new JPanel(new GridLayout(4, 2));
        emailPanel.add(new JLabel("Recipient:"));
        recipientField = new JTextField();
        emailPanel.add(recipientField);
        emailPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        emailPanel.add(subjectField);
        emailPanel.add(new JLabel("Message:"));
        messageArea = new JTextArea();
        emailPanel.add(new JScrollPane(messageArea));
        
        JButton sendButton = new JButton("Send Email");
        emailPanel.add(sendButton);
        
        add(emailPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                emailSender.login(email, password);
                JOptionPane.showMessageDialog(BasicEmailClient.this, "Logged in successfully!");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipient = recipientField.getText();
                String subject = subjectField.getText();
                String messageBody = messageArea.getText();

                try {
                    emailSender.sendMail(recipient, subject, messageBody);
                    JOptionPane.showMessageDialog(BasicEmailClient.this, "Email sent successfully!");
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(BasicEmailClient.this, "Error sending email.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	BasicEmailClient gui = new BasicEmailClient();
            gui.setVisible(true);
        });
    }
}
