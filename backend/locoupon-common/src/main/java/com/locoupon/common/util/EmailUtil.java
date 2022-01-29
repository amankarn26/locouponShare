package com.locoupon.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

	private static String displayFromEmail = "no_reply@ttn.com";
	private static final Logger logger = LoggerFactory.getLogger(UtilFuncs.class);

	public static void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);

			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(displayFromEmail, "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(displayFromEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			logger.debug("Message is ready");
			Transport.send(msg);

			logger.debug("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendAttachmentEmail(Session session, String toEmail, String subject, String body,
			String filepath) {

		logger.debug("sendAttachmentEmail");
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(displayFromEmail, "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(displayFromEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(body);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			String filename = UtilFuncs.getfileNameFromPath(filepath);
			DataSource source = new FileDataSource(filepath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport.send(msg);
			logger.debug("EMail Sent Successfully with attachment!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}