package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


public interface DocumentDao {

	Document add(Document doc);

	List<Document> getAllDocsOfType(String docType);

	ErrorCodes update(Document doc);

	Document getDocByPath(String path);

	void delete(Document doc);

	List<Document> getAll();

	Document getById(Long id);
	
}