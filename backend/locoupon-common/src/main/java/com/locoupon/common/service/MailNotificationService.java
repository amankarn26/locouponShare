package com.locoupon.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.MailRequest;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.OrderObj;


@Service
public interface MailNotificationService {
	void sendMail(MailRequest request) throws Exception;

	Map<String, String> getMailConfig();

	void setMailConfig(Map<String, String> cMap);

	ErrorCodes sendGroupMail(MailRequest request, Request reqType);

	void uploadMailPDF(MailRequest request) throws Exception;

	void sendMailSuperAdmin(OrderObj obj, ErrorCodes reqError) throws Exception;

}
