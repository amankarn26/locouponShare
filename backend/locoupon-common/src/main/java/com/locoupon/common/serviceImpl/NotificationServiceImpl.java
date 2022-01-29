package com.locoupon.common.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Notification;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.NotificationFor;
import com.locoupon.common.model.NotificationMap;
import com.locoupon.common.service.NotificationService;


@Service
public class NotificationServiceImpl implements NotificationService{

	@Override
	public ErrorCodes addNotificans(Notification noti, ErrorCodes reqError) {
		NotificationMap map = new NotificationMap();
		if (noti.getNotificationFor() == NotificationFor.type.CUSTOMER) {
			noti.setNotid(map.getUniqueId());
			map.addCustomerNotification(noti.getId(), noti);
			reqError = ErrorCodes.SUCCESS;
		}

		if (noti.getNotificationFor() == NotificationFor.type.FIRM) {
			noti.setNotid(map.getUniqueId());
			map.addVendorNotification(noti.getId(), noti);
			reqError = ErrorCodes.SUCCESS;
		}

		if (noti.getNotificationFor() == NotificationFor.type.DELIVERY_PERSON) {
			noti.setNotid(map.getUniqueId());
			map.addDeliveryNotification(noti.getId(), noti);
			reqError = ErrorCodes.SUCCESS;
		}
		return reqError;
	}

	@Override
	public List<Notification> getNotifications(Long id, NotificationFor.type stype, ErrorCodes reqError) {
		NotificationMap map = new NotificationMap();
		List<Notification> keySet = null;
		if (stype == NotificationFor.type.CUSTOMER) {
			keySet = (List<Notification>) map.getCustomerNotifications(id);
			reqError = ErrorCodes.SUCCESS;
		}

		if (stype == NotificationFor.type.FIRM) {
			keySet = (List<Notification>) map.getVendorNotifications(id);
			reqError = ErrorCodes.SUCCESS;
		}

		if (stype == NotificationFor.type.DELIVERY_PERSON) {
			keySet = (List<Notification>) map.getDeliveryNotifications(id);
			reqError = ErrorCodes.SUCCESS;
		}
		return keySet;
	}

	@Override
	public ErrorCodes removeNotifications(Long id, Long notificationid, NotificationFor.type stype, ErrorCodes reqError) {
		NotificationMap map = new NotificationMap();
		if (stype == NotificationFor.type.CUSTOMER) {
			map.clearCustomer(id, notificationid);
			reqError = ErrorCodes.SUCCESS;
		}

		if (stype == NotificationFor.type.FIRM) {
			map.clearVendor(id, notificationid);
			reqError = ErrorCodes.SUCCESS;
		}

		if (stype == NotificationFor.type.DELIVERY_PERSON) {
			map.clearDelivery(id, notificationid);
			reqError = ErrorCodes.SUCCESS;
		}
		return reqError;
	}

	@Override
	public ErrorCodes removeNotificationsMulti(Long id, NotificationFor.type stype, ErrorCodes reqError) {
		NotificationMap map = new NotificationMap();
		List<Notification> key = getNotifications(id, stype, reqError);
		for (Notification ids : key) {
			if (ids.getNotificationFor() == NotificationFor.type.CUSTOMER) {
				map.clearAllCustomer(ids);
				reqError = ErrorCodes.SUCCESS;
			}

			if (stype == NotificationFor.type.FIRM) {
				map.clearAllVendor(ids);
				reqError = ErrorCodes.SUCCESS;
			}

			if (stype == NotificationFor.type.DELIVERY_PERSON) {
				map.clearAllDelivery(ids);
				reqError = ErrorCodes.SUCCESS;
			}
		}
		return reqError;
	}

}
