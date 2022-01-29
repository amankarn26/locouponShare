package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Customer;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public interface CustomerDao {

	List<Customer> getAllCustomer();

	Customer getCustomerById(Long id);

	Customer checkIfCustomerExists(Long id, String username,
			ErrorCodes reqError);

	ErrorCodes addCustomer(Customer cust);

	ErrorCodes deleteCustomer(Customer dbcust);

	ErrorCodes updateCustomer(Customer dbcust);

	Long getTotalCount(Long firm);

	List<Customer> getAllCustomer(String scity);

	Customer getCustomerByName(String name);

	Long getCustIDByName(String name);

	Customer getCustomerByEmail(String username);

	Customer checkCustomerExistByMobile(String mob, ErrorCodes reqError);

	List<Customer> getAllCustomerByPincode(String pincode);
	
}