package com.core.util;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Emailer {

	private final String logFileName;
	private final String logDir;
	private final String subject;
	private final String body;
	private String protocol;
	private String serviceHandler;
	private String serviceHostName;
	private String dispatcherPortNumber;
	private String dispatcherUserName;
	private String dispatcherPassword;
	private final List<String> recipients;
	private static final ExecutorService emailThPool = Executors.newFixedThreadPool(2);

	// protected static Logger logger = Logger.getLogger(Emailer.class);

	public Emailer(final String logFileName, final String logDir,
			final String subject, final String body,
			final List<String> recipients) {
		// recipients.add(to);
		this.logFileName = logFileName;
		this.logDir = logDir;
		this.subject = subject;
		this.body = body;
		this.recipients = recipients;
		// emailThPool.submit(this);
	}

	public void sendEmail() throws Exception {

		final boolean debug = false;

		final FPropsLoader pl = new FPropsLoader("mail");
		final Properties props = new Properties();

		protocol = pl.getValue("protocol");
		serviceHandler = pl.getValue("serviceHandler");
		serviceHostName = pl.getValue("serviceHostName");
		dispatcherPortNumber = pl.getValue("dispatcherPortNumber");
		dispatcherUserName = pl.getValue("dispatcherUserName");
		dispatcherPassword = pl.getValue("dispatcherPassword");

		if (("smtp").equals(protocol)) {
			props.put("mail.smtp.host", serviceHostName);
			props.put("mail.smtp.port", dispatcherPortNumber);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
		} else if (("smtps").equals(protocol)) {
			props.put("mail.smtps.host", serviceHostName);
			props.put("mail.smtps.port", dispatcherPortNumber);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.starttls.enable", "true");
		}

		final Authenticator auth = new SMTPAuthenticator();

		final javax.mail.Session session = javax.mail.Session
				.getDefaultInstance(props, auth);
		session.setDebug(debug);

		// create a message
		final Message msg = new MimeMessage(session);

		// set the from and to address
		final InternetAddress addressFrom = new InternetAddress(serviceHandler);
		msg.setFrom(addressFrom);

		final InternetAddress[] addressTo = new InternetAddress[recipients
				.size()];
		for (int i = 0; i < recipients.size(); i++) {
			addressTo[i] = new InternetAddress(recipients.get(i));
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		// msg.setDescription(body);
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Description Modified Parameters To allow setting body content
		// Part 1 : Set Body text : Fill the message
		messageBodyPart.setText(body);
		messageBodyPart.setContent(body, "text/html");

		final Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		// If message has No 'logDir' / No 'logFileName'
		if (!(null == logFileName || null == logDir)) {

			messageBodyPart = new MimeBodyPart();
			final DataSource source = new FileDataSource(logDir + "/"
					+ logFileName);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(logFileName);
			multipart.addBodyPart(messageBodyPart);
			// msg.setContent(body, "text/plain");
		}

		msg.setContent(multipart);

		Transport tr = session.getTransport("smtp");
		if (("smtps").equals(protocol)) {
			tr = session.getTransport("smtps");
		}

		tr.connect(serviceHostName, dispatcherUserName, dispatcherPassword);

		tr.sendMessage(msg, msg.getAllRecipients());
		tr.close();
		//log.info("Successfully Sent the e-mail");
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(dispatcherUserName,
					dispatcherPassword);
		}
	}

	public void run() {

		try {
			sendEmail();
		} catch (final Exception e) {
		}

	}

	public void shutDown() {
		emailThPool.shutdown();
	}
}
