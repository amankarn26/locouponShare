package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@Entity()
@Table(name = "address")
//@Cacheable("address")
public class Address implements Serializable {

	private static final long serialVersionUID = -770732063917826084L;

	private Long id;
	private String line1;
	private String line2;
	private String city;
	private String pincode;
	private String states;
	private String country;
	private Double latitude;
	private Double longitude;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(Address address) {
		this.setLine1(address.getLine1());
		this.setLine2(address.getLine2());
		this.setCity(address.getCity());
		this.setPincode(address.getPincode());
		this.setStates(address.getPincode());
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	@XmlElement(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "line1")
	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	@XmlElement(name = "line2")
	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement(name = "pincode")
	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@XmlElement(name = "states")
	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	@XmlElement(name = "latitute")
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@XmlElement(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

}
