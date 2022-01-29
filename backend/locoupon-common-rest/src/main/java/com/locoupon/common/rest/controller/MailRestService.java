package com.locoupon.common.rest.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.entity.MailRequest;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.MailContentData;
import com.locoupon.common.service.MailNotificationService;

@RestController
@RequestMapping(value = "/mail", produces = "application/json")
public class MailRestService {
	private static final Logger logger = LoggerFactory.getLogger(MailRestService.class);

	@Autowired
	private MailNotificationService mailServ;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity sendMail(@RequestBody MailRequest request) {

		logger.debug("Send Email Begin");
		MailContentData content = request.getContentData();
		if (content != null) {
			if (content.getBody() == null) {
				content.setBody("Test mail...");
			}
			if (content.getSubject() == null) {
				content.setSubject("Test mail");
			}
		}
		if (request.getTo() == null) {
			logger.debug("to Email is null");
		}
		try {
			mailServ.sendMail(request);
			JSONObject json = new JSONObject();
			json.put("Status", RequestTypes.ErrorCodes.MAIL_SENT_SUCCESS);
			return new ResponseEntity(json, HttpStatus.OK);
		} catch (Exception ex) {
			logger.debug("Exception in Send Mail:", ex);
		}
		return null;

	}

	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public ResponseEntity configMail(@RequestBody Map<String, String> cMap) {
		mailServ.setMailConfig(cMap);
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public ResponseEntity getMailConfig() {
		Map<String, String> cMap = mailServ.getMailConfig();
		return new ResponseEntity(cMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/groupmail", method = RequestMethod.POST)
	public ResponseEntity sendGroupMail(@RequestBody MailRequest request) {

		logger.debug("sendMail data:" + request);
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.SEND;

		try {
			reqError = mailServ.sendGroupMail(request, reqType);
			logger.debug("sendMail End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in sendMail " + e);
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/sendPDF", method = RequestMethod.POST)
	public ResponseEntity uploadMailPDF(@RequestBody MailRequest request) {

		logger.debug("Generate Pdf Begin");

		if (request.getPdfid() == null) {
			logger.debug("pdf id is null");
		}
		try {
			mailServ.uploadMailPDF(request);
			JSONObject json = new JSONObject();
			json.put("Status", RequestTypes.ErrorCodes.PDFGENERATE_SUCCESS.toString());
			return new ResponseEntity(json, HttpStatus.OK);
		} catch (Exception ex) {
			logger.debug("Exception in Generate Pdf:", ex);
		}
		return null;

	}

}
