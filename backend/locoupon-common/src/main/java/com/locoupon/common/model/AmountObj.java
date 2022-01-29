package com.locoupon.common.model;

import java.util.ArrayList;
import java.util.List;

import com.locoupon.common.entity.Order;
import com.locoupon.common.enums.AccountType;
import com.locoupon.common.enums.AccountType.type;


public class AmountObj {

	private Long id;
	private String name;
	private String account;
	private AccountType.type atype;
	private Double amount;
	private Double scharge;
	private String Status;
	private String ifsccode;
	private String branch;
	private String bankname;
	private List<Long> oids = new ArrayList<Long>();

	private List<Order> order = new ArrayList<Order>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public AccountType.type getAtype() {
		return atype;
	}

	public void setAtype(AccountType.type atype) {
		this.atype = atype;
	}

	public List<Long> getOids() {
		return oids;
	}

	public void setOids(List<Long> oids) {
		this.oids = oids;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Double getScharge() {
		return scharge;
	}

	public void setScharge(Double scharge) {
		this.scharge = scharge;
	}

}