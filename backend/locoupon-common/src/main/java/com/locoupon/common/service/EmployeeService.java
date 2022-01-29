
package com.locoupon.common.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Employee;
import com.locoupon.common.entity.QCost;
import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface EmployeeService {

	List<Employee> getAllEmployee();

	Employee getEmployeeById(Long id);

	ErrorCodes validateAddEmployee(Employee cust, Request reqType);

	ErrorCodes addEmployee(Employee cust, Request reqType);

	ErrorCodes deleteEmployee(Employee dbcust, Request reqType);

	Employee validateUpdateEmployee(Employee cust, Request reqType, ErrorCodes reqError);

	ErrorCodes updateEmployee(Employee dbcust, Request reqType);

	Employee checkIfEmployeeExists(Employee cust, ErrorCodes reqError);
	
	Employee getEmployeeByEmail(String username);

	ErrorCodes updateEWallet(Long Employee, QCost qcost);

}
