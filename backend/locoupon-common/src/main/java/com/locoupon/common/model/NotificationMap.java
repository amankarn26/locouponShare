package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.locoupon.common.entity.Notification;

public class NotificationMap {
	private static final Logger logger = LoggerFactory.getLogger(NotificationMap.class);
	private static Map<Long, List<Notification>> vendornotifymap = Collections
			.synchronizedMap(new HashMap<Long, List<Notification>>());

	private static Map<Long, List<Notification>> customernotifymap = Collections
			.synchronizedMap(new HashMap<Long, List<Notification>>());

	private static Map<Long, List<Notification>> deliverynotifymap = Collections
			.synchronizedMap(new HashMap<Long, List<Notification>>());

	private static int notificationid = 1;
	private List<Notification> notifications;

	public int getUniqueId() {
		return this.notificationid++;
	}

	public void addVendorNotification(Long vendor, Notification notice) {

		List<Notification> notifications = null;
		if (vendornotifymap.containsKey(vendor)) {
			notifications = vendornotifymap.get(vendor);
			notifications.add(notice);
		} else {
			notifications = new ArrayList<>();
			notifications.add(notice);
			vendornotifymap.put(vendor, notifications);
		}

	}

	public void addCustomerNotification(Long customer, Notification notice) {

		List<Notification> notifications = null;
		if (customernotifymap.containsKey(customer)) {
			notifications = customernotifymap.get(customer);
			notifications.add(notice);
		} else {
			notifications = new ArrayList<>();
			notifications.add(notice);
			customernotifymap.put(customer, notifications);
		}

	}

	public void addDeliveryNotification(Long dperson, Notification notice) {

		List<Notification> notifications = null;
		if (deliverynotifymap.containsKey(dperson)) {
			notifications = deliverynotifymap.get(dperson);
			notifications.add(notice);
		} else {
			notifications = new ArrayList<>();
			notifications.add(notice);
			deliverynotifymap.put(dperson, notifications);
		}

	}

	public List<Notification> getVendorNotifications(Long vendor) {
		return vendornotifymap.get(vendor);
	}

	public List<Notification> getCustomerNotifications(Long customer) {
		return customernotifymap.get(customer);
	}

	public List<Notification> getDeliveryNotifications(Long dperson) {
		return deliverynotifymap.get(dperson);
	}

	public void clearVendor(Long vendor, Long notificationid) {
		List<Notification> notifications = getVendorNotifications(vendor);
		Notification tobedeleted = search(notificationid, notifications);
		notifications.remove(tobedeleted);
	}

	public void clearCustomer(Long customer, Long notificationid) {
		List<Notification> notifications = getCustomerNotifications(customer);
		Notification tobedeleted = search(notificationid, notifications);
		notifications.remove(tobedeleted);
	}

	public void clearDelivery(Long dperson, Long notificationid) {
		List<Notification> notifications = getDeliveryNotifications(dperson);
		Notification tobedeleted = search(notificationid, notifications);
		notifications.remove(tobedeleted);
	}

	private Notification search(Long notificationid, List<Notification> notifications) {
		Notification noti = null;
		try {
			for (Notification notify : notifications) {

				if (notificationid == notify.getNotid()) {
					noti = notify;
				}
			}
		} catch (Exception e) {
			logger.debug("Notification Delete:" + e);
			return null;
		}

		return noti;
	}

	public void clearAllCustomer(Notification ids) {
		customernotifymap.remove(ids.getId());
	}

	public void clearAllVendor(Notification ids) {
		vendornotifymap.remove(ids.getId());
	}

	public void clearAllDelivery(Notification ids) {
		deliverynotifymap.remove(ids.getId());
	}

}
