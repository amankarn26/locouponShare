package com.locoupon.common.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.inouvelle.supply.delegator.MailNotificationService;
//import com.inouvelle.supply.model.MailRequest;
//import com.inouvelle.supply.task.AutowireHelper;

public class NotificationClient {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(NotificationClient.class);

//	@Autowired
//	MailNotificationService mailService;
//
//	public void sendMail(final MailRequest request) {
//		AutowireHelper.autowire(this, this.mailService);
//		Thread t1 = new Thread(new Runnable() {
//			public void run() {
//				try {
//					mailService.sendMail(request);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		t1.start();
		/*
		 * try { mailService.sendMail(request); } catch (Exception e) {
		 * logger.error("Exception ",e); }
		 */
//	}
}
