package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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


@XmlRootElement(name = "category")
@Entity()
@Table(name = "category")
//@Cacheable("category")
public class Category implements Serializable {

	private static final long serialVersionUID = -770740963917826084L;

	private Long id;
	private String name;
	private Document photo;

	private String status;
	private RequestTypes.ErrorCodes errCode;

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

}
