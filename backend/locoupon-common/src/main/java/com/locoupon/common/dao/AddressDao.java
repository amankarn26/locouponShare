package com.locoupon.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.Address;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public interface AddressDao {

	ErrorCodes add(Address address);

	ErrorCodes delete(Address address);

	ErrorCodes update(Address dbaddr);

	Address getAddress(Long id);

	Address find(Address address);
	
}