package com.locoupon.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.CustomerType;

@XmlRootElement(name = "discount")
@Entity()
@Table(name = "discount")
public class Discount implements Serializable {

	private static final long serialVersionUID = -770740963917826084L;

	private Long id;
	private String name;
	private CustomerType.type type;
	private Document photo;
	private String couponcode;
	private Double discount;
	private Date validtill;
	private Integer minvalue;
	private String status;
	private RequestTypes.ErrorCodes errCode;

	public Discount()
	{
		this.discount = 0.0;
		this.minvalue = 0;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public RequestTypes.ErrorCodes getErrCode() {
		return errCode;
	}

	public void setErrCode(RequestTypes.ErrorCodes errCode) {
		this.errCode = errCode;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "photo")
	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	@XmlElement(name = "couponcode")
	public String getCouponcode() {
		return couponcode;
	}

	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}

	@XmlElement(name = "discount")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@XmlElement(name = "validtill")
	public Date getValidtill() {
		return validtill;
	}

	public void setValidtill(Date validtill) {
		this.validtill = validtill;
	}

	@XmlElement(name = "minvalue")
	public Integer getMinvalue() {
		return minvalue;
	}

	public void setMinvalue(Integer minvalue) {
		this.minvalue = minvalue;
	}

	
	@Enumerated(EnumType.STRING)
	@XmlElement(name = "type")
	public CustomerType.type getType() {
		return type;
	}

	public void setType(CustomerType.type type) {
		this.type = type;
	}
	
	

}
