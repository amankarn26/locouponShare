package com.locoupon.common.service;

import java.util.List;

import com.locoupon.common.entity.Notification;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.NotificationFor.type;

public interface NotificationService {

	ErrorCodes removeNotificationsMulti(Long id, type stype, ErrorCodes reqError);

	ErrorCodes addNotificans(Notification noti, ErrorCodes reqError);

	List<Notification> getNotifications(Long id, type stype, ErrorCodes reqError);

	ErrorCodes removeNotifications(Long id, Long notificationid, type stype, ErrorCodes reqError);
	
}
