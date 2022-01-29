package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.CategoryDao;
import com.locoupon.common.entity.Category;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);

	@Override
	public List<Category> getAllCategory() {
		String qlString = "from Category order by name";
		TypedQuery<Category> query = entityManager.createQuery(qlString, Category.class);
		return query.getResultList();
	}

	@Override
	public Category getCategoryById(Long id) {
		try {
			String qlString = "from Category where id=:id";
			TypedQuery<Category> query = entityManager.createQuery(qlString, Category.class);
			query.setParameter("id", id);

			Category firm = query.getSingleResult();
			;
			return firm;
		} catch (Exception e) {
			logger.error("Exception in getCategoryById " + id + e);
			return null;
		}
	}

	@Override
	public ErrorCodes addCategory(Category firm) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(firm);
		} catch (Exception e) {
			logger.debug("Exception in saving Category ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public ErrorCodes deleteCategory(Category dbfirm) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbfirm);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Category " + dbfirm.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public Category checkIfCategoryExists(String name, ErrorCodes reqError) {
		Category depart = null;
		try {
			String qlString = "from Category where name=:name";
			TypedQuery<Category> query = entityManager.createQuery(qlString, Category.class);
			query.setParameter("name", name);

			depart = query.getSingleResult();
			if (null == depart) {
				reqError.setValue(ErrorCodes.CATEGORY_DOES_NOT_EXIST.toString());
				reqError = ErrorCodes.CATEGORY_DOES_NOT_EXIST;
				return null;
			} else {
				return depart;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfExists " + name + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.CATEGORY_DOES_NOT_EXIST.toString());
			reqError = ErrorCodes.CATEGORY_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public ErrorCodes updateCategory(Category dbfirm) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbfirm);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Category " + dbfirm.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public boolean checkCategoryInUsed(Long id, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Category> getAllCategory(Integer index, Integer limit) {
		String qlString = "from Category order by name";
		TypedQuery<Category> query = entityManager.createQuery(qlString, Category.class);
		query.setFirstResult(index);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
