package com.locoupon.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.Gender;
import com.locoupon.common.model.CustomerType;


@XmlRootElement(name = "customer")
@Entity()
@Table(name = "customer")
//@Cacheable("customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = -770735063917826084L;

	private Long id;
	private String name;
	private String phone;
	private String email;
	private Timestamp addedon;
	private String city;
	private Login login;
	private Document photo;
	private Address address;
	private EWallet ewallet;
	private String pan;
	private String aadhar;
	private Set<Document> files = new HashSet<Document>(5);

	//Transient
	private String version;

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

	@XmlElement(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElement(name = "addedon")
	public Timestamp getAddedon() {
		return addedon;
	}

	public void setAddedon(Timestamp addedon) {
		this.addedon = addedon;
	}

	@XmlElement(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "login")
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@Transient
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "photo")
	public Document getPhoto() {
		return photo;
	}

	public void setPhoto(Document photo) {
		this.photo = photo;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ewallet")
	public EWallet getEwallet() {
		return ewallet;
	}

	public void setEwallet(EWallet ewallet) {
		this.ewallet = ewallet;
	}
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	@JoinTable(name = "customer_files", joinColumns = {
			@JoinColumn(name = "customer", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "doc", nullable = false, updatable = false) })
	public Set<Document> getFiles() {
		return files;
	}

	public void setFiles(Set<Document> photos) {
		this.files = photos;
	}

	public void addFiles(Document d) {
		files.add(d);
	}

	public void deleteFiles(Document d) {
		files.remove(d);
	}

	@XmlElement(name = "pan")
	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	

	@XmlElement(name = "aadhar")
	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	
	
	
}
