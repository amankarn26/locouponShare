package com.locoupon.common.serviceImpl;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.locoupon.common.dao.ConfigDao;
import com.locoupon.common.dao.EmailTemplateDao;
import com.locoupon.common.entity.Config;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.MailRequest;
import com.locoupon.common.entity.Order;
import com.locoupon.common.enums.InvoiceType;
import com.locoupon.common.enums.PdfContants;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.MailContentData;
import com.locoupon.common.model.OrderObj;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.MailNotificationService;
import com.locoupon.common.util.UtilFuncs;

@Service
public class MailNotificationServiceImpl implements MailNotificationService  {

	private Logger logger = LoggerFactory.getLogger(MailNotificationServiceImpl.class);

	private static Map<String, String> emailConfigMap;
	private static String MAIL_TO;
	private static String MAIL_TO_CC;

	@Autowired
	private EmailTemplateDao templateService;

	@Autowired
	private ConfigDao configDao;

	
	@Autowired
	private DocumentService docServ;
	

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;
	
	@Value("${macdocpath}")
	private String macdocpath;

//	@PostConstruct
	private void loadMailConfig() {
		this.logger.debug("Loading email configuration :");
		String[] propsArray = { "mail.smtp.host", "mail.smtp.port", "mail.username", "mail.password", "mail.enabled",
				"mail.to.cc", "mail.to" };
		List<Config> cList = configDao.getConfigList(propsArray);
		emailConfigMap = new HashMap<String, String>();
		for (Config c : cList) {
			emailConfigMap.put(c.getName(), c.getValue());
		}
	}

	private synchronized Session getSession() {
		this.logger.info("Configuring SMTP session");
		Map<String, String> cMap = getMailConfig();
		if ((cMap.containsKey("mail.enabled")) && (cMap.get("mail.enabled") != null)
				&& (((String) cMap.get("mail.enabled")).equalsIgnoreCase("false"))) {
			return null;
		}
		Properties props = new Properties();
		String host = cMap.get("mail.smtp.host");
		String port = cMap.get("mail.smtp.port");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		//props.put("mail.smtp.ssl.enable", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		this.logger.info("Configuring TLS for:"+host+":port:"+port);
		//props.put("mail.smtp.ssl.trust", host);
		//props.put("mail.smtp.socketFactory.port", port);
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		

		if ((cMap.containsKey("mail.to")) && (cMap.get("mail.to") != null)) {
			MAIL_TO = (String) cMap.get("mail.to");
		} else {

		}
		if ((cMap.containsKey("mail.to.cc")) && (cMap.get("mail.to.cc") != null)) {
			MAIL_TO_CC = (String) cMap.get("mail.to.cc");
		}
		final String username = (String) cMap.get("mail.username");
		final String password = (String) cMap.get("mail.password");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		this.logger.info("SMTP configuration completed");
		return session;
	}

	private void setReciepients(MimeMessage mimeMessage, Message.RecipientType type, String recipients)
			throws MessagingException {
		String[] recList = recipients.split(";");
		InternetAddress[] mailAddress = new InternetAddress[recList.length];
		for (int i = 0; i < recList.length; i++) {
			mailAddress[i] = new InternetAddress(recList[i]);
		}
		mimeMessage.setRecipients(type, mailAddress);

	}

	private void addAttachment(Multipart multipart, String filename) throws MessagingException {
		BodyPart attachmentBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		attachmentBodyPart.setDataHandler(new DataHandler(source));
		attachmentBodyPart.setFileName(source.getName());
		multipart.addBodyPart(attachmentBodyPart);
	}

	public void sendMail(MailRequest request) throws Exception {
		// this.logger.debug("Request :::::::::: " + request.getTo());
		if (request.isHtmlContent() && request.getTemplateId() == null) {
			request.setTemplateId("qdefault");
		}
		if (request.getTo() == null) {
			request.setTo(MAIL_TO);
		}

		Session session = getSession();
		if (session == null) {

			this.logger.debug("Email notification disabled");
			return;
		}

		String from = emailConfigMap.get("mail.username");
		this.logger.debug("from:" + from);
		MimeMessage message = new MimeMessage(session);
		InternetAddress[] recipientAddress = UtilFuncs.getMailRecipients(request.getTo());

		message.setRecipients(Message.RecipientType.TO, recipientAddress);
		message.setFrom(new InternetAddress(from, emailConfigMap.get("mail.displayname")));

		// setReciepients(message, Message.RecipientType.TO, request.getTo());
		if (request.getCc() != null) {
			// request.setCc(MAIL_TO_CC);
			// this.logger.debug("Setting up cc");
			setReciepients(message, Message.RecipientType.CC, request.getCc());
		}
		// this.logger.debug("Setting template");

		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		MailContentData contentData = request.getContentData();
		if (request.isHtmlContent()) {
			if (request.getTemplateId() != null) {
				String templateContent = this.templateService.getTemplate(request.getTemplateId(), request);
				messageBodyPart.setContent(templateContent, "text/html");
				// messageBodyPart.setContent(templateContent, "richtext/html");

			} else {
				messageBodyPart.setContent(contentData.getBody(), "text/html");
				// messageBodyPart.setContent(contentData.getBody(), "richtext/html");
			}
		} else {

			messageBodyPart.setContent(contentData.getBody(), "text/plain");
		}

		// this.logger.info("Setting Subject in Mail:" + contentData.getSubject());
		multipart.addBodyPart(messageBodyPart);
		message.setSubject(contentData.getSubject());
		if (contentData.getAttachmentFiles() != null) {
			for (String file : contentData.getAttachmentFiles()) {
				addAttachment(multipart, file);
			}
		}
		// this.logger.info("Message Ready Send to:" + request.getTo());
		message.setContent(multipart);
		Transport.send(message);

		this.logger.info("Mail Sent Successfully. to " + request.getTo());
	}

	public Map<String, String> getMailConfig() {
		if (emailConfigMap == null) {
			loadMailConfig();
		}
		return emailConfigMap;
	}

	public void setMailConfig(Map<String, String> cMap) {
		for (String key : cMap.keySet()) {
			Config c = new Config();
			c.setName(key);
			c.setValue(cMap.get(key));
			configDao.saveOrUpdate(c);
		}
		emailConfigMap = cMap;
	}

	public ErrorCodes sendGroupMail(MailRequest request) {
		return null;/*

		String logprefix = "sendGroupMail ";
		logger.debug(logprefix + " Begin to: " + request.getRole().getRole());

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Userrole dbrole = null;
		if (null != request.getRole()) {
			try {
				dbrole = roleServ.getRole(request.getRole().getRole());
				if (null == dbrole) {
					logger.debug("User Role does not exist", request.getRole().getRole());
					reqError = ErrorCodes.USERROLE_DOES_NOT_EXIST;
					return reqError;

				}
				request.setRole(dbrole);// add role to notify object
			} catch (Exception e) {
				logger.error("Exception in User Role", e);
				reqError = ErrorCodes.ERROR_EXCEPTION;
				return reqError;
			}
		}

		try {
			sendMail(request);
		} catch (Exception e) {
			logger.debug(logprefix + " Exception in sendmail:" + e);
		}

		logger.debug(logprefix + " End");
		return reqError;

	*/}

	/*
	 * public void sendMailSuperAdmin(OrderObj obj, ErrorCodes reqError) throws
	 * Exception { final String SUBJECT = "New Order Added"; final String CONTENT =
	 * "JGL: New Order Generated: " + obj.getInvoice() + " From " + obj.getVendor()
	 * + " And Order ID: " + obj.getOrderid(); Session session = getSession(); if
	 * (session == null) { this.logger.debug("Email notification disabled"); return;
	 * } String to = emailConfigMap.get("mail.to"); String from =
	 * emailConfigMap.get("mail.username"); this.logger.debug("from:" + from);
	 * MimeMessage message = new MimeMessage(session); InternetAddress[]
	 * recipientAddress = UtilFuncs.getMailRecipients(to);
	 * message.setRecipients(Message.RecipientType.TO, recipientAddress);
	 * message.setFrom(new InternetAddress(from,
	 * emailConfigMap.get("mail.displayname"))); message.setSubject(SUBJECT);
	 * message.setText(CONTENT); try { Transport.send(message); } catch
	 * (MessagingException e) { this.logger.info("Mail Sent UnSuccessfully..." + e);
	 * }
	 * 
	 * reqError = ErrorCodes.SUCCESS; this.logger.info("Mail Sent Successfully. to "
	 * + obj.getVendor()); }
	 */

	public void uploadMailPDF(MailRequest request) throws Exception {
		// this.logger.debug("Request :::::::::: " + request.getTo());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		if (request.isHtmlContent() && request.getTemplateId() == null) {
			request.setTemplateId("qdefault");
		}

		String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
		String fullDirPath = UtilFuncs.concatenateDirName(docPath, PdfContants.DOCUMENTSPATH);
		String templateContent = this.templateService.getTemplate(request.getTemplateId(), request);
		String docnames = null;

		if (request.getTemplateId() != null) {
			UtilFuncs.createRecurrDirs(docPath, "invoice");

			if (request.getTemplateId() != null && request.getInvoicetype() == InvoiceType.type.CUSTOMER) {
				docnames = "customer_invoice_" + request.getPdfid();
			} else {
				docnames = "invoice_" + request.getPdfid();
			}

			HtmlConverter.convertToPdf(templateContent,
					new FileOutputStream(fullDirPath + docnames + PdfContants.PDFNAME));
			//logger.debug(templateContent);
			docnames = docnames + PdfContants.PDFNAME;

			this.logger.info("Pdf Generate Successfully." + request.getPdfid());

//			this.sendMail(request);
		} else {

			this.logger.info("TemplateId is null " + request.getTemplateId());

		}

		String relativepath = PdfContants.DOCUMENTSPATH + docnames;
		String docname = docnames;
		
		if(null!= request.getPdfid())
		{
		if (request.getInvoicetype() == InvoiceType.type.CUSTOMER) {

			Long ordid = UtilFuncs.getLongfromStr(request.getPdfid());
			this.logger.info("ordid ===========" + ordid);
			//Order order = orderServ.getOrderById(ordid);
			Document doc = new Document(relativepath, docname);
			Document dbdoc = docServ.add(doc);
			//order.setInvoice(dbdoc);
			//logger.debug("setting invoice"+order.getInvoice().getId());
			//order.setStatus(StatusType.type.DELIVERED);
			//orderDao.updateOrder(order);
			//logger.debug("after setting invoice"+order.getInvoice().getId());

		}
		}
	}

/*	
*/
	@Override
	public ErrorCodes sendGroupMail(MailRequest request, Request reqType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMailSuperAdmin(OrderObj obj, ErrorCodes reqError)throws Exception {
		final String SUBJECT = "New Order Added";
		final String CONTENT = "JGL: New Order Generated: " + obj.getInvoice() + " From " + obj.getVendor()
				+ " And Order ID: " + obj.getOrderid();
		Session session = getSession();
		if (session == null) {
			this.logger.debug("Email notification disabled");
			return;
		}
		String to = emailConfigMap.get("mail.to");
		String from = emailConfigMap.get("mail.username");
		this.logger.debug("from:" + from);
		MimeMessage message = new MimeMessage(session);
		InternetAddress[] recipientAddress = UtilFuncs.getMailRecipients(to);
		message.setRecipients(Message.RecipientType.TO, recipientAddress);
		message.setFrom(new InternetAddress(from, emailConfigMap.get("mail.displayname")));
		message.setSubject(SUBJECT);
		message.setText(CONTENT);
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			this.logger.info("Mail Sent UnSuccessfully..." + e);
		}

		reqError = ErrorCodes.SUCCESS;
		this.logger.info("Mail Sent Successfully. to " + obj.getVendor());
	}
}
