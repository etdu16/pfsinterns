import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
	private String userEmail;
	private String password;

	public void login(String ue, String p) {
		userEmail = ue;
		password = p;
	}

	public void sendMail(String recepient, String sub, String msg) throws MessagingException {
		Properties properties = new Properties();

		properties.put("mail.smtp.auth", true); // enables authentication (javamail api will use username and password)
												// to connect to smtp server
		properties.put("mail.smtp.starttls.enable", true); // activates starttls which enables emails to be sent
															// securely
		properties.put("mail.smtp.host", "smtp.gmail.com"); // specifies the smtp server being used
		properties.put("mail.smtp.port", "587"); // specifies the port

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userEmail, password);
			}
		});

		Message message = prepareMessage(session, recepient, sub, msg);
		
		Transport.send(message);
		System.out.println("Successfully sent email.");
	}

	private Message prepareMessage(Session session, String recepient, String subject, String text) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject(subject);
			message.setText(text);
			return message;
		} catch (Exception e) {
			//
		}
		return null;
	}
}
