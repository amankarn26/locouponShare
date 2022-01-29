package com.locoupon.common.service;

import org.springframework.stereotype.Service;

import com.locoupon.common.model.SmsRequest;


@Service
public interface SmsNotificationService {

	String sendSms(SmsRequest request);

}
