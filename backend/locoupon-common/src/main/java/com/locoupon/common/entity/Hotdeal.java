
package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotdeals")
@Entity()
@Table(name = "hotdeals")
//@Cacheable("deliverypool")
public class Hotdeal implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private Long dish;
	private Double discount;
	
	//Transiant
	private Document photo;
	private String dname;
	private Double rateperunit;
	
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@XmlElement(name = "discount")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Transient
	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	
	/*@Transient
	public String getProductphoto() {
		return productphoto;
	}

	public void setProductphoto(String productphoto) {
		this.productphoto = productphoto;
	}*/




	

	@XmlElement(name = "dish")
	public Long getDish() {
		return dish;
	}

	public void setDish(Long dish) {
		this.dish = dish;
	}

	@Transient
	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Transient
	public Double getRateperunit() {
		return rateperunit;
	}
	public void setRateperunit(Double rateunit) {
		this.rateperunit = rateunit;
	}
	
	
	
}
