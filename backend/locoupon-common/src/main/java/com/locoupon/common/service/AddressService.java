package com.locoupon.common.service;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Address;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface AddressService {

	ErrorCodes add(Address address, Request reqType);

	Address find(Address address, Request reqType);

	Address update(Long id, Address address);

	ErrorCodes delAddr(Address address, Request reqType);

}
