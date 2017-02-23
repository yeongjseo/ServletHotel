package common;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	public class MyAuthentication extends Authenticator {
	    String id;
	    String password;
	    
	    public MyAuthentication(String id, String password) {
	        this.id = id;
	        this.password = password;
	    }
	    public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id, password);
	    }
	}

	// ME to Me Email
	public void send(EmailDO email) throws Exception {
		Authenticator auth;
		Properties prop;
		InternetAddress from;
		InternetAddress to;
		String toEmailAddress = email.getToEmailAddress();
		String toId = toEmailAddress.substring(0, toEmailAddress.indexOf("@")); 
		String toPw = email.getToEmailPassword();
		StringBuffer content = new StringBuffer();
		
		System.out.printf("id %s, pw %s\n", toId, toPw);
		
		auth = new MyAuthentication(toId, toPw);
		
		prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.user", toEmailAddress);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.port", "465");
		// prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.port", "465"); 
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		prop.put("mail.smtp.socketFactory.fallback", "false");    
		
		Session session = Session.getDefaultInstance(prop, auth);
		// session.setDebug(true);
		
		MimeMessage msg = new MimeMessage(session);

		msg.setSentDate(new Date());
		// MUST BE EMAIL ADDRESS FORMAT
		String sender = "Simple Hotel<" + email.getFromEmailAddress() + ">"; 
		from = new InternetAddress(sender);
		msg.setFrom(from);

		to = new InternetAddress(toEmailAddress); 

		msg.setHeader("content-Type", "text/html");
		msg.setRecipient(Message.RecipientType.TO, to);
		msg.setSubject(email.getTitle(), "UTF-8");
		
		content.append("질문자: " + email.getFromName() + "\r\n");
		content.append("메일주소: " + email.getFromEmailAddress() + "\r\n");
		content.append("질문내용: \r\n" + email.getContent());
		msg.setText(content.toString(), "UTF-8");
		
		Transport.send(msg);

	}
}
