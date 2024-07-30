package com.cst8288.finalproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	/**
	 * This method will send an email to a subscriber from the companies email address.
	 * We will set up an SMTP host, port, and authentication.
	 * 
	 * The authentication will be taken from the same properties file used for database authentication.
	 */
	public void sendEmail(String recipient, String content) {
		
		//store the companies email that alerts will be sent from in a string varaible
		final String sender = "wehatefoodwaste@gmail.com";
		
		Properties properties = new Properties();
        
        //input path to read from database.properties file
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("data/database.properties")) {
            if (in == null) {
                throw new IOException("Properties file not found");
            }
            properties.load(in);
            System.out.println("Properties file loaded for email server connection...");
        } catch(IOException e) {
            System.out.println("Error reading properties: " + e.getMessage());
        }
        
        //set the SMTP password variable tot he value obtained from database.properties
        String password = properties.getProperty("appPass");
        //get system properties
        Properties mailProperties = System.getProperties();
        
        //set up mail server properties. enable TLS, set port, etc.
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        //create a new session object using mail API authenticator
        Session session = Session.getInstance(mailProperties, new Authenticator(){
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(sender, password);
        	}
        });
        
        try {
        	//create a default MIME message
        	MimeMessage MIME = new MimeMessage(session);
        	
        	//set header of the message to the senders email
        	MIME.setFrom(new InternetAddress(sender));
        	
        	//set the recipient in the message heasder
        	MIME.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        	
        	//set the subject of the header
        	MIME.setSubject("New FoodAlert!");
        	
        	//s=conent to send in email
        	MIME.setText(content);
        	
        	//send the created message
        	Transport.send(MIME);
        	
        	//log that message was sent successfully
        	System.out.println("Email sent successfully!");
        	
        	
        }
        //catch exception if email doesnt send succesfully
        catch(MessagingException e) {
        	System.out.println("Error sending email: " + e.getMessage());
        }
        
        
	}
	
}
