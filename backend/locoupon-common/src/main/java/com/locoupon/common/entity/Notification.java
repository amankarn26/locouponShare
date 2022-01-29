package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.locoupon.common.model.NotificationFor;
import com.locoupon.common.model.NotificationFor.type;

@Entity
public class Notification implements Serializable {
	private static final long serialVersionUID = -770567063917826084L;

	private Long id;
	private int notid;
	private NotificationFor.type notificationFor;
	private String message;
	private String toMobile;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getNotid() {
		return notid;
	}

	public void setNotid(int notid) {
		this.notid = notid++;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationFor.type getNotificationFor() {
		return notificationFor;
	}

	public void setNotificationFor(NotificationFor.type notificationFor) {
		this.notificationFor = notificationFor;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

}
