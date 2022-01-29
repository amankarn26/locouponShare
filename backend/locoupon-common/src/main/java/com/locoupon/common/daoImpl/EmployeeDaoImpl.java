package com.locoupon.common.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.EmployeeDao;
import com.locoupon.common.entity.Employee;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class EmployeeDaoImpl implements EmployeeDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Override
	public List<Employee> getAllEmployee() {
		String qlString = "from Employee order by name";
		TypedQuery<Employee> query = entityManager.createQuery(qlString, Employee.class);
		return query.getResultList();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		try {
			String qlString = "from Employee where id=:id";
			TypedQuery<Employee> query = entityManager.createQuery(qlString, Employee.class);
			query.setParameter("id", id);
			Employee cust = query.getSingleResult();
			return cust;
		} catch (Exception e) {
			logger.error("Exception in getEmployeeById " + id, e);
			return null;
		}
	}

	@Override
	public Employee checkIfEmployeeExists(Long id, String email, ErrorCodes reqError) {
		Employee cust = null;
		try {
			String qlString = "from Employee where id=:id or login.email=:email ";
			TypedQuery<Employee> query = entityManager.createQuery(qlString, Employee.class);
			query.setParameter("email", email);
			query.setParameter("id", id);
			cust = query.getSingleResult();
			if (null == cust) {
				reqError = ErrorCodes.EMPLOYEE_DOES_NOT_EXIST;
				return null;
			} else {
				return cust;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfEmployeeExists " + email + " Exception: " + e.getMessage());
			reqError = ErrorCodes.EMPLOYEE_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public ErrorCodes addEmployee(Employee cust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(cust);
		} catch (Exception e) {
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return ErrorCodes.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public ErrorCodes deleteEmployee(Employee dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			dbcust = entityManager.merge(dbcust);
			entityManager.remove(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Employee " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes updateEmployee(Employee dbcust) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbcust);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Employee " + dbcust.getId(), e);
			reqError = ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}


	@Override
	public Employee getEmployeeByEmail(String email) {
		try {
			String qlString = "from Employee e where e.login.email=:email";
			TypedQuery<Employee> query = entityManager.createQuery(qlString, Employee.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in getEmployeeByEmail " + email, e);
			return null;
		}
	}

}