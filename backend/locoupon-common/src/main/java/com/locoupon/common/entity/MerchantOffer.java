package com.locoupon.common.entity;

import java.io.Serializable;
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

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.StatusType;

@XmlRootElement(name = "merchant_offer")
@Entity()
@Table(name = "merchant_offer")
//@Cacheable("prod_stock")
public class MerchantOffer implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private Long merchant;
	private Long product;
	private String mname;
	private String details;
	private String location;
	private String carpassing;
	private Double price;
	private Double gst;
	private StatusType.type status;
	private EnumBoolean.type available;
	
	private String reqstatus;
	private RequestTypes.ErrorCodes errCode;

	
	/*public ProdStock(PoInwardDetails poid, String lotno, int bookedunits) {
		this.setProduct(poid.getProduct());

		this.setMfg(poid.getMfg());
		this.setPname(poid.getPname());
		this.setRateperunit(poid.getRateperunit());

	}*/
	


	public MerchantOffer() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "product")
	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	@Transient
	public RequestTypes.ErrorCodes getErrCode() {
		return errCode;
	}

	public void setErrCode(RequestTypes.ErrorCodes errCode) {
		this.errCode = errCode;
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "available")
	public EnumBoolean.type getAvailable() {
		return available;
	}

	public void setAvailable(EnumBoolean.type available) {
		this.available = available;
	}
	

	@Transient
	public String getReqstatus() {
		return reqstatus;
	}

	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}


	
	@XmlElement(name = "gst")
	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@XmlElement(name = "merchant")
	public Long getMerchant() {
		return merchant;
	}

	public void setMerchant(Long merchant) {
		this.merchant = merchant;
	}

	@XmlElement(name = "mname")
	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	@XmlElement(name = "details")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@XmlElement(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement(name = "carpassing")
	public String getCarpassing() {
		return carpassing;
	}

	public void setCarpassing(String carpassing) {
		this.carpassing = carpassing;
	}

	@XmlElement(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "status")
	public StatusType.type getStatus() {
		return status;
	}

	public void setStatus(StatusType.type status) {
		this.status = status;
	}

	
	
}