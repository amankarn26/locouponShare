package com.locoupon.common.model;

public class SmsRequest {
	private String user;
	private String password;
	private String senderid;
	private String channel;
	private String DCS;
	private String flashsms;
	private String route;

	private String toMobile;
	private String message;

//	public void Account() {
//		this.setUser(user="inouapps");
//		this.setPassword(password="inou123apps");
//		this.setSenderid(senderid="INOUAP");
//		this.setChannel(channel="Trans");
//		this.setDCS(DCS="0");
//		this.setFlashsms(flashsms="0");
//		this.setRoute(route="7");
//	}	

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDCS() {
		return DCS;
	}

	public void setDCS(String dCS) {
		DCS = dCS;
	}

	public String getFlashsms() {
		return flashsms;
	}

	public void setFlashsms(String flashsms) {
		this.flashsms = flashsms;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

}
