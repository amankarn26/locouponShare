package com.locoupon.common.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locoupon.common.enums.LoginType;


@XmlRootElement(name = "login")
@Entity()
@Table(name = "login")
public class Login implements Serializable {

	private static final long serialVersionUID = -770730063917826084L;

	private Long id;
	private LoginType.type type;

	private String email;
	private String password;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@XmlElement(name = "type")
	public LoginType.type getType() {
		return type;
	}

	public void setType(LoginType.type type) {
		this.type = type;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
