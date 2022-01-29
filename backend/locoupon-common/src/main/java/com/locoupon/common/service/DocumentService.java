package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;


@Service
public interface DocumentService {

	Document add(Document set);

	List<Document> getAll();

	Document getDoc(Long id);

	Document getDocByPath(String path);

	ErrorCodes deleteDoc(Document dbDoc);

	ErrorCodes update(Document dbDoc);

	List<Document> getAllDocsOfType(String type);

	ErrorCodes deleteDir(String directoryName);

	Document validateUpdateDoc(Document doc, ErrorCodes reqError);

	ErrorCodes validateAndAdd(Document fileDoc);

	ErrorCodes deleteObjectsFromIds(List<Long> ids, Request reqType);

	ErrorCodes deleteFile(String docpath);

	ErrorCodes validate(Document doc);

	String storeFile(MultipartFile file, String dir,String width,String height);

}
