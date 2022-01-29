package com.locoupon.common.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.model.SmsRequest;
import com.locoupon.common.service.SmsNotificationService;

@RestController
@RequestMapping(value = "/sms", produces = "application/json")
public class SMSRestService {

	private static final Logger logger = LoggerFactory.getLogger(SMSRestService.class);

	@Autowired
	private SmsNotificationService smsServ;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity<SmsRequest> sendSms(@RequestBody SmsRequest request) {
		logger.debug("Send sms Begin");

		if (request.getToMobile() == null && request.getMessage() == null) {
			logger.debug("to mobile no is null");
			return new ResponseEntity(RequestTypes.ErrorCodes.NULL_INPUT, HttpStatus.OK);
		}

		try {
			ReturnObject ret = new ReturnObject();
			String resp = smsServ.sendSms(request);
			ret.setStrval(resp);
			return new ResponseEntity(ret, HttpStatus.OK);

		} catch (Exception ex) {

			logger.debug("Exception in Send Sms:", ex);

			return new ResponseEntity(ErrorCodes.ERROR_EXCEPTION, HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/send/bulk", method = RequestMethod.POST)
	public ResponseEntity sendBulkSms(@RequestBody List<SmsRequest> request) {
		logger.debug("sendBulkSms Begin");

		if (request.size() < 1) {
			logger.debug("to mobile no is null");
			return new ResponseEntity(RequestTypes.ErrorCodes.NULL_INPUT, HttpStatus.OK);
		}

		try {
			ReturnObject ret = new ReturnObject();
			for (SmsRequest rest : request) {
				String resp = smsServ.sendSms(rest);
				ret.setStrval(resp);
			}

			return new ResponseEntity(ret, HttpStatus.OK);

		} catch (Exception ex) {

			logger.debug("Exception in Send Sms:", ex);

			return new ResponseEntity(ErrorCodes.ERROR_EXCEPTION, HttpStatus.OK);

		}

	}

}
