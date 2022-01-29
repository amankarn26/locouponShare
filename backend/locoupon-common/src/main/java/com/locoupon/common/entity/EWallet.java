package com.locoupon.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ewallet")
@Entity()
@Table(name = "ewallet")
public class EWallet implements Serializable {

	private static final long serialVersionUID = -770740963917826084L;

	private Long id;
	private Integer points;
	private Double total;
	private Double availed;
	private Double balance;
	private Date validtill;
	
	public EWallet()
	{
		this.setPoints(0);
		this.setAvailed(0.0);
		this.setBalance(0.0);
		this.setTotal(0.0);
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "points")
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@XmlElement(name = "availed")
	public Double getAvailed() {
		return availed;
	}

	public void setAvailed(Double availed) {
		this.availed = availed;
	}

	@XmlElement(name = "balance")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@XmlElement(name = "validtill")
	public Date getValidtill() {
		return validtill;
	}

	public void setValidtill(Date validtill) {
		this.validtill = validtill;
	}

	@XmlElement(name = "total")
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

}
