package com.locoupon.common.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.entity.Notification;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.NotificationFor;
import com.locoupon.common.service.NotificationService;


@RestController
@RequestMapping(value = "/notification", produces = "application/json")
public class NotificationRestService {
	private static final Logger logger = LoggerFactory.getLogger(NotificationRestService.class);

	@Autowired
	private NotificationService notificationServ;

	@RequestMapping(value = "/cache", method = RequestMethod.POST)
	public ResponseEntity CachingOrder(@RequestBody Notification noti) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
//		logger.debug("addNotification Start:");
		try {
			reqError = notificationServ.addNotificans(noti, reqError);
		} catch (Exception e) {
			logger.debug("addNotification " + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(reqError, HttpStatus.OK);
	}

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public ResponseEntity getCachingOrder(@ModelAttribute("id") Long id, @ModelAttribute("type") String type) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
//		logger.debug("getNotification Start:" + id);
		List<Notification> notice = null;
		try {
			NotificationFor.type stype = NotificationFor.type.valueOf(type);
			notice = notificationServ.getNotifications(id, stype, reqError);
		} catch (Exception e) {
			logger.debug("getNotification " + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}

//		logger.debug("getNotification End");
		return new ResponseEntity(notice, HttpStatus.OK);

	}

	@RequestMapping(value = "/cache", method = RequestMethod.DELETE)
	public ResponseEntity cachingOrderDelete(@ModelAttribute("id") Long id, @ModelAttribute("notid") Long notiid,
			@ModelAttribute("type") String type) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
//		logger.debug("NotificationDelete Start:" + id);

		try {
			NotificationFor.type stype = NotificationFor.type.valueOf(type);
			reqError = notificationServ.removeNotifications(id, notiid, stype, reqError);
		} catch (Exception e) {
			logger.debug("NotificationDelete " + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}

//		logger.debug("NotificationDelete End");
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

	}

	@RequestMapping(value = "/addM", method = RequestMethod.POST)
	public ResponseEntity addCachingOrder(@RequestBody List<Notification> noti) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
//		logger.debug("addNotification Start:");
		try {
			for (Notification am : noti) {
				reqError = notificationServ.addNotificans(am, reqError);
			}
		} catch (Exception e) {
			logger.debug("addNotification " + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(reqError, HttpStatus.OK);
	}

	@RequestMapping(value = "/cacheM", method = RequestMethod.DELETE)
	public ResponseEntity cachingAllOrderDelete(@ModelAttribute("id") Long id, @ModelAttribute("type") String type) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
//		logger.debug("NotificationDelete Start:");

		try {
			NotificationFor.type stype = NotificationFor.type.valueOf(type);
			reqError = notificationServ.removeNotificationsMulti(id, stype, reqError);

		} catch (Exception e) {
			logger.debug("NotificationDelete " + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}

//		logger.debug("NotificationDelete End");
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
	}
}
