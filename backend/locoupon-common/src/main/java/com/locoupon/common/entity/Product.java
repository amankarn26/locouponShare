package com.locoupon.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.DishType;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.StatusType;


@XmlRootElement(name = "product")
@Entity()
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private String name;
	private String prodtype;
	private String brand;
	private String details;
	private Double actualprice;
	private Double pointprice;
	private StatusType.type status;
	
	private List<MerchantOffer> merchantoffer = new ArrayList<MerchantOffer>();
	private Set<Document> photos = new HashSet<Document>(5);
	
	private String reqstatus;
	private RequestTypes.ErrorCodes errCode = ErrorCodes.NONE;

	public Product() {
	//	this.priority = 1;

	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	@JoinTable(name = "product_photos", joinColumns = {
			@JoinColumn(name = "product", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "doc", nullable = false, updatable = false) })
	public Set<Document> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Document> photos) {
		this.photos = photos;
	}

	public void addPhotos(Document d) {
		photos.add(d);
	}

	public void deletePhotos(Document d) {
		photos.remove(d);
	}

	@Transient
	public RequestTypes.ErrorCodes getErrCode() {
		return errCode;
	}

	public void setErrCode(RequestTypes.ErrorCodes errCode) {
		this.errCode = errCode;
	}

	@Transient
	public String getReqstatus() {
		return reqstatus;
	}

	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}


	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "prodtype")
	public String getProdtype() {
		return prodtype;
	}

	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}

	@XmlElement(name = "details")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	
	@XmlElement(name = "actualprice")
	public Double getActualprice() {
		return actualprice;
	}

	public void setActualprice(Double actualprice) {
		this.actualprice = actualprice;
	}

	
	@XmlElement(name = "pointprice")
	public Double getPointprice() {
		return pointprice;
	}

	public void setPointprice(Double pointprice) {
		this.pointprice = pointprice;
	}


	@XmlElement(name = "brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@XmlElement(name = "status")
	@Enumerated(EnumType.STRING)
	public StatusType.type getStatus() {
		return status;
	}

	public void setStatus(StatusType.type status) {
		this.status = status;
	}

	@Transient
	public List<MerchantOffer> getMerchantoffer() {
		return merchantoffer;
	}

	public void setMerchantoffer(List<MerchantOffer> merchantoffer) {
		this.merchantoffer = merchantoffer;
	}

		

}
