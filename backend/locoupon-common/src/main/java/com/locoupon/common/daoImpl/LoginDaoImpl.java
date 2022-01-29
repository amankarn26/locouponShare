package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.LoginDao;
import com.locoupon.common.entity.Login;
import com.locoupon.common.enums.LoginType;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;



@Transactional
@Service
public class LoginDaoImpl implements LoginDao {

	private static final Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ErrorCodes add(Login login) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(login);
		} catch (Exception e) {
			logger.debug("Exception in saving Login ", e);
			return RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
		}

		return reqError;
	}

	@Override
	public ErrorCodes delete(Login login) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			login = entityManager.merge(login);
			entityManager.remove(login);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete " + login.getEmail(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes update(Login dblog) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dblog);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update " + dblog.getId() + "Exception:" + e.getMessage());
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public Login get(Long id) {
		try {
			String qlString = "from Login where id=:id";
			TypedQuery<Login> query = entityManager.createQuery(qlString, Login.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in getLogin" + id + e.getMessage());
			return null;
		}
	}

	@Override
	public Login verifylogin(String email, String password) {
		try {
			String qlString = "from Login where email=:email";
			if (null != password) {
				qlString = "from Login where email=:email and password=:password";
			}
			TypedQuery<Login> query = entityManager.createQuery(qlString, Login.class);
			query.setParameter("email", email);
			if (null != password) {
				query.setParameter("password", password);
			}
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in verifylogin " + email + " Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public Login getlogin(String email) {
		try {
			String qlString = "from Login where email=:email";
			TypedQuery<Login> query = entityManager.createQuery(qlString, Login.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in getlogin " + email + " Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Login> getLoginType(LoginType.type type) {
		try {
			String qlString = "from Login where type=:type";
			TypedQuery<Login> query = entityManager.createQuery(qlString, Login.class);
			query.setParameter("type", type);
			List<Login> ltype = query.getResultList();
			return ltype;
		} catch (Exception e) {
			logger.error("Exception in getLoginType " + type, e);
			return null;
		}
	}

	

}
