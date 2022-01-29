package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.RequestTypes;

@XmlRootElement(name = "sysconfig")
@Entity()
@Table(name = "sysconfig")
public class SystemConfig implements Serializable {
	private static final long serialVersionUID = -770732063917826084L;

	private Long id;
	private Long firm;

	private String name;

	private String value;

	private RequestTypes.ErrorCodes errCode;
	private String reqstatus;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "firm")
	public Long getFirm() {
		return firm;
	}

	public void setFirm(Long firm) {
		this.firm = firm;
	}

	@XmlElement(name = "value")

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getReqstatus() {
		return reqstatus;
	}

	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}

	@Transient
	public RequestTypes.ErrorCodes getErrCode() {
		return errCode;
	}

	public void setErrCode(RequestTypes.ErrorCodes errCode) {
		this.errCode = errCode;
	}

}
