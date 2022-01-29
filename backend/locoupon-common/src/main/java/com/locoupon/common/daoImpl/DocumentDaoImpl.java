package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.DocumentDao;
import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

@Transactional
@Service
public class DocumentDaoImpl implements DocumentDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);

	@Override
	public Document add(Document document) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("Saving Document " + document.getDocname());
		try {
			// entityManager.persist(appl.getAddress());
			entityManager.persist(document);
			logger.debug("Saving Document Success " + document.getDocname());
			return document;
		} catch (Exception e) {
			logger.debug("Exception in saving Document ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Document> getAll() {
		logger.debug("getAllDocuments ");
		String qlString = "from Document order by updatedon desc";
		TypedQuery<Document> query = entityManager.createQuery(qlString, Document.class);

		return query.getResultList();
	}

	@Override
	public void delete(Document doc) {
		logger.debug("delete " + doc.getDocname());
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			doc = entityManager.merge(doc);
			entityManager.remove(doc);
		} catch (Exception e) {
			logger.error("Exception in delete " + doc.getDocname(), e);
			// reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			// return reqError;
		}

	}

	public Document getDoc(Long id) {
		logger.debug("getDocument for id:" + id);
		String qlString = "from Document where id=:id";
		TypedQuery<Document> query = entityManager.createQuery(qlString, Document.class);

		query.setParameter("id", id);
		return query.getSingleResult();
	}

	@Override
	public Document getDocByPath(String path) {
		logger.debug("getDocument for path:" + path);
		String qlString = "from Document where docpath=:path";
		TypedQuery<Document> query = entityManager.createQuery(qlString, Document.class);

		query.setParameter("path", path);
		return query.getSingleResult();
	}

	@Override
	public ErrorCodes update(Document doc) {
		logger.debug("update " + doc.getDocname());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(doc);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update " + doc.getDocname(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public List<Document> getAllDocsOfType(String docType) {

		logger.debug("getAllDocsOfType for type:" + docType);
		String qlString = "from Document where docType=:docType order by updatedon desc";
		TypedQuery<Document> query = entityManager.createQuery(qlString, Document.class);

		query.setParameter("docType", docType);
		return query.getResultList();

	}

	@Override
	public Document getById(Long id) {
		logger.debug("getDocument for id:" + id);
		String qlString = "from Document where id=:id";
		TypedQuery<Document> query = entityManager.createQuery(qlString, Document.class);

		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
