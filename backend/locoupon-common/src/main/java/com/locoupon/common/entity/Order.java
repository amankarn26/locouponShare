package com.locoupon.common.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.PaymentMode;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.StatusType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.NotificationFor;


@XmlRootElement(name = "ordersale")
@Entity()
@Table(name = "ordersale")
public class Order {

	private Long id;
	private Long firm;
	private String invoicenum; // if sale
	private Date invoicedate;
	private Long customer;
	private String custname;
	private String custmobile;
	private String custcity;
	private Address deliveryaddress;
	private QCost qcost;
	private String comments;
	private StatusType.type status;
	private Date expdelivery;
	private Time expdeliverytime;
	private PaymentMode.type paymentmode;
	private EnumBoolean.type favourite;
	private Long deliverypool;
	private String deliveryperson;
	private String deliverymobile;
	private String transactionid;
	private String dpoolemail;
	private Document invoice;
	private EnumBoolean.type isdeliverypaid;
	private EnumBoolean.type isdeliveryaccept;
	private Date addedon;
	private EnumBoolean.type cashcollected;
	private Double totalweight;
	
	private String firmname;
	private EnumBoolean.type isvendorpaid;

	
	private EnumBoolean.type isvendoraccept;
	private NotificationFor.type cancelledbytype;
	private Long cancelledby;
	private String cancelledbyname;
	private String reason;

	
	private Double penalty;

	private List<OrderEntry> oentry = new ArrayList<OrderEntry>();

	//transient
	private String detail;
	private EnumBoolean.type reviewadded;
	private String responsestatus;
	private ErrorCodes reqerror =RequestTypes.ErrorCodes.NONE;
	

	public Order(Order od) {
		this.setId(od.getId());
		this.setComments(od.getComments());
		this.setCustcity(od.getCustcity());
		this.setCustmobile(od.getCustmobile());
		this.setCustname(od.getCustname());
		this.setCustomer(od.getCustomer());
		this.setDeliveryaddress(od.getDeliveryaddress());
		this.setDeliverymobile(od.getDeliverymobile());
		this.setDeliveryperson(od.getDeliveryperson());
		this.setDeliverypool(od.getDeliverypool());
		this.setExpdelivery(od.getExpdelivery());
		this.setExpdeliverytime(od.getExpdeliverytime());
		this.setFavourite(od.getFavourite());
		this.setFirm(od.getFirm());
		this.setInvoice(od.getInvoice());
		this.setInvoicedate(od.getInvoicedate());
		this.setInvoicenum(od.getInvoicenum());
		this.setPaymentmode(od.getPaymentmode());
		this.setQcost(od.getQcost());
		this.setOentry(od.getOentry());
		this.setStatus(od.getStatus());
		this.setTransactionid(od.getTransactionid());

	}

	public Order() {
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

	@XmlElement(name = "firm")
	public Long getFirm() {
		return firm;
	}

	public void setFirm(Long firm) {
		this.firm = firm;
	}

	@XmlElement(name = "custname")
	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "qcost")
	@XmlElement(name = "qcost")
	public QCost getQcost() {
		return qcost;
	}

	public void setQcost(QCost qcost) {
		this.qcost = qcost;
	}

	@XmlElement(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "status")
	public StatusType.type getStatus() {
		return status;
	}

	public void setStatus(StatusType.type status) {
		this.status = status;
	}

	@XmlElement(name = "expdelivery")
	public Date getExpdelivery() {
		return expdelivery;
	}

	public void setExpdelivery(Date expdelivery) {
		this.expdelivery = expdelivery;
	}

	@XmlElement(name = "custmobile")
	public String getCustmobile() {
		return custmobile;
	}

	public void setCustmobile(String custmobile) {
		this.custmobile = custmobile;
	}

	@XmlElement(name = "invoicenum")
	public String getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "paymentmode")
	public PaymentMode.type getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(PaymentMode.type paymentmode) {
		this.paymentmode = paymentmode;
	}

	@Transient
	@XmlElement(name = "oentry")
	public List<OrderEntry> getOentry() {
		return oentry;
	}

	public void setOentry(List<OrderEntry> qsentry) {
		this.oentry = qsentry;
	}

	@XmlElement(name = "custcity")
	public String getCustcity() {
		return custcity;
	}

	public void setCustcity(String custcity) {
		this.custcity = custcity;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deliveryaddress")
	public Address getDeliveryaddress() {
		return deliveryaddress;
	}

	public void setDeliveryaddress(Address deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}

	@XmlElement(name = "expdeliverytime")
	public Time getExpdeliverytime() {
		return expdeliverytime;
	}

	public void setExpdeliverytime(Time expdeliverytime) {
		this.expdeliverytime = expdeliverytime;
	}

	@XmlElement(name = "favourite")
	@Enumerated(EnumType.STRING)
	public EnumBoolean.type getFavourite() {
		return favourite;
	}

	public void setFavourite(EnumBoolean.type favourite) {
		this.favourite = favourite;
	}

	@XmlElement(name = "deliverypool")
	public Long getDeliverypool() {
		return deliverypool;
	}

	public void setDeliverypool(Long deliverypool) {
		this.deliverypool = deliverypool;
	}

	@XmlElement(name = "transactionid")
	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	@XmlElement(name = "customer")
	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	@XmlElement(name = "invoicedate")
	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	@XmlElement(name = "deliveryperson")
	public String getDeliveryperson() {
		return deliveryperson;
	}

	public void setDeliveryperson(String deliveryperson) {
		this.deliveryperson = deliveryperson;
	}

	@XmlElement(name = "deliverymobile")
	public String getDeliverymobile() {
		return deliverymobile;
	}

	public void setDeliverymobile(String deliverymobile) {
		this.deliverymobile = deliverymobile;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice")
	public Document getInvoice() {
		return invoice;
	}

	public void setInvoice(Document invoice) {
		this.invoice = invoice;
	}

	@Transient
	public String getFirmname() {
		return firmname;
	}

	public void setFirmname(String firmname) {
		this.firmname = firmname;
	}

	@Transient
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Transient
	public NotificationFor.type getCancelledbytype() {
		return cancelledbytype;
	}

	public void setCancelledbytype(NotificationFor.type cancelledbytype) {
		this.cancelledbytype = cancelledbytype;
	}

	@Transient
	public Long getCancelledby() {
		return cancelledby;
	}

	public void setCancelledby(Long cancelledby) {
		this.cancelledby = cancelledby;
	}

	@Transient
	public String getCancelledbyname() {
		return cancelledbyname;
	}

	public void setCancelledbyname(String cancelledbyname) {
		this.cancelledbyname = cancelledbyname;
	}

	@Transient
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Transient
	public EnumBoolean.type getIsvendorpaid() {
		return isvendorpaid;
	}

	public void setIsvendorpaid(EnumBoolean.type isvendorpaid) {
		this.isvendorpaid = isvendorpaid;
	}

	@XmlElement(name = "isdeliverypaid")
	@Enumerated(EnumType.STRING)
	public EnumBoolean.type getIsdeliverypaid() {
		return isdeliverypaid;
	}

	public void setIsdeliverypaid(EnumBoolean.type isdeliverypaid) {
		this.isdeliverypaid = isdeliverypaid;
	}

	
	@XmlElement(name = "isdeliveryaccept")
	@Enumerated(EnumType.STRING)
	public EnumBoolean.type getIsdeliveryaccept() {
		return isdeliveryaccept;
	}

	public void setIsdeliveryaccept(EnumBoolean.type isdeliveryaccept) {
		this.isdeliveryaccept = isdeliveryaccept;
	}

	@Transient
	public EnumBoolean.type getIsvendoraccept() {
		return isvendoraccept;
	}

	public void setIsvendoraccept(EnumBoolean.type isvendoraccept) {
		this.isvendoraccept = isvendoraccept;
	}

	@XmlElement(name = "cashcollected")
	@Enumerated(EnumType.STRING)
	public EnumBoolean.type getCashcollected() {
		return cashcollected;
	}

	public void setCashcollected(EnumBoolean.type cashcollected) {
		this.cashcollected = cashcollected;
	}

	@XmlElement(name = "addedon")
	public Date getAddedon() {
		return addedon;
	}

	public void setAddedon(Date addedon) {
		this.addedon = addedon;
	}


	@Transient
	public String getDpoolemail() {
		return dpoolemail;
	}

	public void setDpoolemail(String dpoolemail) {
		this.dpoolemail = dpoolemail;

	}

	@Transient
	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	@Transient
	public String getResponsestatus() {
		return responsestatus;
	}

	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}

	@Transient
	public ErrorCodes getReqerror() {
		return reqerror;
	}

	public void setReqerror(ErrorCodes reqerror) {
		this.reqerror = reqerror;
	}


	@XmlElement(name = "totalweight")
	public Double getTotalweight() {
		return totalweight;
	}

	public void setTotalweight(Double totalweight) {
		this.totalweight = totalweight;
	}

	
	@Transient
	public EnumBoolean.type getReviewadded() {
		return reviewadded;
	}

	public void setReviewadded(EnumBoolean.type reviewadded) {
		this.reviewadded = reviewadded;
	}

	

	
	
	
	

}
