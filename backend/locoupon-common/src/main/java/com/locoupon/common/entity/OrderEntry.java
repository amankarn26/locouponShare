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

import com.locoupon.common.enums.DishType;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RateUnit;


@XmlRootElement(name = "order_entry")
@Entity()
@Table(name = "order_entry")
//@Cacheable("order_entry")
public class OrderEntry implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private Long orderid;
	private String pname;
	private String brand;
	private Double weight;
	private Integer qty;
	private Integer volume;
	private Double amount;
	private RateUnit.type rateunit;
	private Double rateperunit;
	private Double entrytotal;
	private String variant;
	private Double gst;
	private Double gstamt;
	
	//Transiant
	private Document photo;
	private String prodtype;
	
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	@XmlElement(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@XmlElement(name = "rateperunit")
	public Double getRateperunit() {
		return rateperunit;
	}

	public void setRateperunit(Double rateperunit) {
		this.rateperunit = rateperunit;
	}

	@XmlElement(name = "orderid")
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	@XmlElement(name = "entrytotal")
	public Double getEntrytotal() {
		return entrytotal;
	}

	public void setEntrytotal(Double entrytotal) {
		this.entrytotal = entrytotal;
	}

	

	@Transient
	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	@XmlElement(name = "qty")
	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "rateunit")
	public RateUnit.type getRateunit() {
		return rateunit;
	}

	public void setRateunit(RateUnit.type rateunit) {
		this.rateunit = rateunit;
	}



	@XmlElement(name = "variant")
	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Transient
	public String getProdtype() {
		return prodtype;
	}

	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}

	@XmlElement(name = "pname")
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@XmlElement(name = "brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@XmlElement(name = "weight")
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@XmlElement(name = "volume")
	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	@XmlElement(name = "gst")
	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@XmlElement(name = "gstamt")
	public Double getGstamt() {
		return gstamt;
	}

	public void setGstamt(Double gstamt) {
		this.gstamt = gstamt;
	}

	

	
	
	
	
	
	


	
	
}
