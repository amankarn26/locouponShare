package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Employee;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public interface EmployeeDao {

	List<Employee> getAllEmployee();

	Employee getEmployeeById(Long id);

	Employee checkIfEmployeeExists(Long id, String username,
			ErrorCodes reqError);

	ErrorCodes addEmployee(Employee cust);

	ErrorCodes deleteEmployee(Employee dbcust);

	ErrorCodes updateEmployee(Employee dbcust);

	Employee getEmployeeByEmail(String username);

	
}