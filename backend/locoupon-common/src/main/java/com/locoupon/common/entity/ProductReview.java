package com.locoupon.common.entity;

import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.ReviewStatus;

@XmlRootElement(name = "product_reviews")
@Entity()
@Table(name = "product_reviews")
public class ProductReview implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private Long orderid;
	private Long product;
	private Long addedby;
	private String addedbyname;
	private String review;
	private Double rating;
	private ReviewStatus.type status;
	private Timestamp addedon;
	
	private String pname;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	@XmlElement(name = "addedbyname")
	public String getAddedbyname() {
		return addedbyname;
	}

	public void setAddedbyname(String addedbyname) {
		this.addedbyname = addedbyname;
	}

	@XmlElement(name = "review")
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@XmlElement(name = "rating")
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@XmlElement(name = "addedon")
	public Timestamp getAddedon() {
		return addedon;
	}

	public void setAddedon(Timestamp addedon) {
		this.addedon = addedon;
	}

	@XmlElement(name = "addedby")
	public Long getAddedby() {
		return addedby;
	}

	public void setAddedby(Long addedby) {
		this.addedby = addedby;
	}

	

	@XmlElement(name = "status")
	@Enumerated(EnumType.STRING)
	public ReviewStatus.type getStatus() {
		return status;
	}

	public void setStatus(ReviewStatus.type status) {
		this.status = status;
	}

	@XmlElement(name = "product")
	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	@XmlElement(name = "orderid")
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	@Transient
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	

	
}
