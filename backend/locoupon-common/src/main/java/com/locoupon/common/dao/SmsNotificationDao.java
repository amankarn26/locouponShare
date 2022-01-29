package com.locoupon.common.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.locoupon.common.model.SmsRequest;

@Service
public class SmsNotificationDao {
	private static Logger logger = LoggerFactory.getLogger(SmsNotificationDao.class);

	public String sendSms(SmsRequest request) {

		logger.debug("Request :: " + request.getToMobile() + ": Message ::" + request.getMessage());

		String resp = sendSMS(request);
		if ("SUCCESS".equalsIgnoreCase(resp)) {
			logger.info("Sms Sent Successfully. to " + request.getToMobile());
		}
		return resp;
	}

	public static String sendSMS(SmsRequest request) {

		String retval = null;
		String msg = request.getMessage();

		try {
			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode("inouapps", "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("inou123apps", "UTF-8");
			data += "&" + URLEncoder.encode("number", "UTF-8") + "="
					+ URLEncoder.encode(request.getToMobile(), "UTF-8");
			data += "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
//			data += "&" + URLEncoder.encode("senderid", "UTF-8") + "=" + URLEncoder.encode("WECARE", "UTF-8");
			data += "&" + URLEncoder.encode("senderid", "UTF-8") + "=" + URLEncoder.encode("INOUAP", "UTF-8");
//			data += "&" + URLEncoder.encode("channel", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8");
			data += "&" + URLEncoder.encode("channel", "UTF-8") + "=" + URLEncoder.encode("Trans", "UTF-8");
			if ("8".equalsIgnoreCase(request.getDCS())) {
				data += "&" + URLEncoder.encode("DCS", "UTF-8") + "=" + URLEncoder.encode("8", "UTF-8");
			} else {
				data += "&" + URLEncoder.encode("DCS", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			}
			data += "&" + URLEncoder.encode("flashsms", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			data += "&" + URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode("7", "UTF-8");
			URL url = new URL("http://103.16.142.249/api/mt/SendSMS" + "?" + data);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			BufferedReader rd;
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				retval += line;
			}
			logger.info("Sms Sent Successfully." + retval);
			rd.close();

			return "SUCCESS";
		} catch (IOException ex) {

			return "ERROR_SMS";
		}

	}

}