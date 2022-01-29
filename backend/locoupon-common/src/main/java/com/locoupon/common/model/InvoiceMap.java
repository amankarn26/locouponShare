package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceMap {
	private static final Logger logger = LoggerFactory.getLogger(NotificationMap.class);
	private static Map<Long, List<InvoiceObj>> vendornotifymap = Collections
			.synchronizedMap(new HashMap<Long, List<InvoiceObj>>());

	private static int notificationid = 1;

	public int getUniqueId() {
		return this.notificationid++;
	}

	public void addInvoice(Long vendor, InvoiceObj notice) {

		List<InvoiceObj> invoice = null;
		if (vendornotifymap.containsKey(vendor)) {
			invoice = vendornotifymap.get(vendor);
			invoice.add(notice);
		} else {
			invoice = new ArrayList<>();
			invoice.add(notice);
			vendornotifymap.put(vendor, invoice);
		}

	}

	public List<InvoiceObj> getVendorInvoice(Long vendor) {
		return vendornotifymap.get(vendor);
	}

	public void clearVendor(Long vendor, String notificationid) {
		List<InvoiceObj> notifications = getVendorInvoice(vendor);
		InvoiceObj tobedeleted = search(notificationid, notifications);
		notifications.remove(tobedeleted);
	}

	private InvoiceObj search(String notificationid, List<InvoiceObj> notifications) {
		InvoiceObj noti = null;
		try {
			for (InvoiceObj notify : notifications) {

				if (notificationid == notify.getInvoiceid()) {
					noti = notify;
				}
			}
		} catch (Exception e) {
			logger.debug("Notification Delete:" + e);
			return null;
		}

		return noti;
	}

	public void clearAllVendor(Long ids) {
		vendornotifymap.remove(ids);
	}
}
