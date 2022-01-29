package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

public class GoogleMap {

	private List<String> destination = new ArrayList<String>();
	private String error;
	private List<String> origin = new ArrayList<String>();
	private String text;
	private String time;
	private String states;

	public List<String> getDestination() {
		return destination;
	}

	public void setDestination(List<String> destination) {
		this.destination = destination;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getOrigin() {
		return origin;
	}

	public void setOrigin(List<String> origin) {
		this.origin = origin;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

}
