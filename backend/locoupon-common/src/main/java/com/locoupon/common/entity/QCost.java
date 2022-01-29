package com.locoupon.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "qcost")
@Entity()
@Table(name = "qcost")
//@Cacheable("qcost")
public class QCost {

	private Long id;
	private Double baseprice;
	private Double gstpercentage;
	private Integer totalitem;
	private Double gstamt;
	private Double delivery;
	private Double finalamt;
	private Integer discountrate;
	private Double discountvalue;
	private Double cancellationcharges;
	private Double refundamt;

	public QCost() {
		this.baseprice = 0.0;
		this.totalitem = 0;
		this.delivery = 0.0;
		this.discountvalue = 0.0;
		this.discountrate = 0;
		this.gstpercentage = 0.0;
		this.gstamt = 0.0;
		this.finalamt = 0.0;
		this.refundamt = 0.0;
		this.cancellationcharges=0.0;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "gstamt")
	public Double getGstamt() {
		return gstamt;
	}

	public void setGstamt(Double gstamt) {
		this.gstamt = gstamt;
	}

	@XmlElement(name = "finalamt")
	public Double getFinalamt() {
		return finalamt;
	}

	public void setFinalamt(Double finalamt) {
		this.finalamt = finalamt;
	}

	@XmlElement(name = "baseprice")
	public Double getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(Double baseprice) {
		this.baseprice = baseprice;
	}

	@XmlElement(name = "totalitem")
	public Integer getTotalitem() {
		return totalitem;
	}

	public void setTotalitem(Integer totalitem) {
		this.totalitem = totalitem;
	}

	@XmlElement(name = "delivery")
	public Double getDelivery() {
		return delivery;
	}

	public void setDelivery(Double delivery) {
		this.delivery = delivery;
	}

	@XmlElement(name = "discountrate")
	public Integer getDiscountrate() {
		return discountrate;
	}

	public void setDiscountrate(Integer discountrate) {
		this.discountrate = discountrate;
	}

	@XmlElement(name = "discountvalue")
	public Double getDiscountvalue() {
		return discountvalue;
	}

	public void setDiscountvalue(Double discountvalue) {
		this.discountvalue = discountvalue;
	}

	

	@XmlElement(name = "refundamt")
	public Double getRefundamt() {
		return refundamt;
	}

	public void setRefundamt(Double refundamt) {
		this.refundamt = refundamt;
	}

	
	@XmlElement(name = "gstpercentage")
	public Double getGstpercentage() {
		return gstpercentage;
	}

	public void setGstpercentage(Double gstpercentage) {
		this.gstpercentage = gstpercentage;
	}

	@XmlElement(name = "cancellationcharges")
	public Double getCancellationcharges() {
		return cancellationcharges;
	}

	public void setCancellationcharges(Double cancellationcharges) {
		this.cancellationcharges = cancellationcharges;
	}
	
	

}
