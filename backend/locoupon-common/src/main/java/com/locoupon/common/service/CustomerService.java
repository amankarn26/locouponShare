package com.locoupon.common.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Customer;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.BulkUpdateCust;
import com.locoupon.common.model.CompactCustomer;

@Service
public interface CustomerService {

	List<Customer> getAllCustomer();

	Customer getCustomerById(Long id);

	ErrorCodes validateAddCustomer(Customer cust, Request reqType);

	ErrorCodes addCustomer(Customer cust, Request reqType);

	ErrorCodes deleteCustomer(Customer dbcust, Request reqType);

	Customer validateUpdateCustomer(Customer cust, Request reqType, ErrorCodes reqError);

	ErrorCodes updateCustomer(Customer dbcust, Request reqType);

	Customer checkIfCustomerExists(Customer cust, ErrorCodes reqError);
	
	ErrorCodes checkIfCustomerIsINUse(Customer cust, ErrorCodes reqError);

	Customer getCustomerByEmail(String email);

	Long getAllCount(Long firm);

	List<Customer> getAllCustomer(String scity);

	Customer getCustomerByName(String name);

	Long getCustIDByName(String custname);

	Customer checkCustomerExistByMobile(String mob, ErrorCodes reqError);

	List<Customer> getAllCustomerByPincode(String pincode);

	ErrorCodes updateEWallet(Long customer, QCost qcost);

	ErrorCodes addFilesToCustomer(Customer dbproduct, Set<Document> photos, Request reqType);

	ErrorCodes deleteMultipleCustomerFiles(Customer dbproduct, List<Long> ids, Request reqType);

}
