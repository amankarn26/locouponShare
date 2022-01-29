package com.locoupon.common.service;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.MailRequest;

@Service
public interface EmailTemplateService {
	String getTemplate(String templateId, MailRequest request) throws Exception;

}
