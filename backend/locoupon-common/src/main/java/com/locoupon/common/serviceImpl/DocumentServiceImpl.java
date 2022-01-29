package com.locoupon.common.serviceImpl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.locoupon.common.dao.DocumentDao;
import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.util.AppStringUtils;
import com.locoupon.common.util.UtilFuncs;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentDao docDao;

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;
	
	@Value("${macdocpath}")
	private String macdocpath;

	private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	public Document add(Document document) {
		String logprefix = "addDocument ";
		logger.error(logprefix + " Begin");
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		Date ts = new Date();
		document.setUpdatedon(new Timestamp(ts.getTime()));

		Document doc = docDao.add(document);
		logger.error(logprefix + " End");
		return doc;
	}

	public ErrorCodes validate(Document document) {
		String logprefix = "validate Document ";
		logger.error(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = AppStringUtils.validateLongStr(document.getDocname(), reqError);
		if (reqError != RequestTypes.ErrorCodes.NONE) {
			reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCNAME.toString());
			return reqError;
		}

		if (null != document.getDocpath()) {
			reqError = AppStringUtils.validatePathStr(document.getDocpath(), reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCPATH.toString());
				return reqError;
			}
		}

		if (null != document.getDoctype()) {
			reqError = AppStringUtils.validateStr(document.getDoctype(), reqError);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCTYPE.toString());
				return reqError;
			}
		}
		logger.error(logprefix + " End");
		return reqError;
	}

	public List<Document> getAll() {
		logger.error("getAll Docs Start");
		return docDao.getAll();
	}


	public Document getDoc(Long id) {
		logger.error("get Doc Start for Id:" + id);
		return docDao.getById(id);
	}

	public Document getDocByPath(String path) {
		logger.error("get Doc Start for path:" + path);
		return docDao.getDocByPath(path);
	}

	public ErrorCodes deleteDoc(Document dbDoc) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.error("Deleting doc:" + dbDoc.getDocname());

		if (null != dbDoc.getDocpath() && !(dbDoc.getDocpath().trim().isEmpty())) {
			logger.error("Doc path :" + dbDoc.getDocpath());
			String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
			logger.error("docPath after fetching cat home:" + docPath);
			String filepath = UtilFuncs.getFilePath(docPath, dbDoc.getDocpath());// dirpath+"/"+caseName;
			logger.error("filepath after append docpath:" + filepath);

			File file = new File(filepath);
			if (file.exists()) {
				if (file.delete()) {
					logger.error(file.getName() + " doc file is deleted!");
				} else {
					logger.error("Delete operation failed for doc file.");
					reqError = ErrorCodes.FAILED_TO_DELETE_DOC_FILE;
					return reqError;
				}
			}
			logger.error("Deleting corr db object :" + dbDoc.getDocname());
			try {
				docDao.delete(dbDoc);
			} catch (Exception e) {
				logger.error("Exception in deleting corr db object :" + dbDoc.getDocname());
				reqError = ErrorCodes.ERROR_EXCEPTION;
				return reqError;
			}
		}

		return reqError;
	}

	public ErrorCodes update(Document dbDoc) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.error("update doc:" + dbDoc.getDocname());
		reqError = docDao.update(dbDoc);
		return reqError;
	}

	public List<Document> getAllDocsOfType(String type) {
		logger.error("getAllDocsOfType:" + type);
		return docDao.getAllDocsOfType(type);
	}

	public ErrorCodes deleteDir(String directoryName) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.error("deleteDir:" + directoryName);
		File directory = new File(directoryName);
		if (!directory.exists()) {

			logger.error("Directory does not exist." + directoryName);
			return reqError;

		} else {
			try {
				delete(directory);
			} catch (IOException e) {
				logger.error("Exception in delete dir" + directoryName, e);
				return ErrorCodes.ERROR_EXCEPTION;
			}
		}
		return reqError;

	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				logger.error("Directory is empty, is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.error("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			logger.error("File is deleted : " + file.getAbsolutePath());
		}
	}

	public BufferedImage resizeImage(Image img, int w, int h) {
		final BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		// below three lines are for RenderingHints for better image quality at cost of
		// higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(img, 0, 0, w, h, null);
		graphics2D.dispose();
		return bufferedImage;
	}

	public Document validateUpdateDoc(Document doc, ErrorCodes reqError) {
		String logprefix = " validateUpdateDoc ";
		logger.error(logprefix + " Begin");
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		Document dbdoc = docDao.getById(doc.getId());

		if (null == dbdoc) {
			return null;
		}

		if (null != doc.getDocdesc() && !(doc.getDocdesc().trim().isEmpty())) {
			dbdoc.setDocdesc(doc.getDocdesc());
		}

		if (null != doc.getDocname() && !(doc.getDocname().trim().isEmpty())) {
			dbdoc.setDocname(doc.getDocname());
		}

		if (null != doc.getDocpath()) {
			reqError = AppStringUtils.validatePathStr(doc.getDocpath(), reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCPATH.toString());
				return null;
			} else {
				dbdoc.setDocpath(doc.getDocpath());
			}
		}
		Date ts = new Date();
		dbdoc.setUpdatedon(new Timestamp(ts.getTime()));
		logger.error(logprefix + " End");
		return dbdoc;
	}

	public ErrorCodes validateAndAdd(Document document) {
		String logprefix = "validateAndAdd Document ";
		logger.error(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = AppStringUtils.validateLongStr(document.getDocname(), reqError);
		if (reqError != RequestTypes.ErrorCodes.NONE) {
			reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCNAME.toString());
			return reqError;
		}

		if (null != document.getDocpath()) {
			reqError = AppStringUtils.validatePathStr(document.getDocpath(), reqError);

			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCPATH.toString());
				return reqError;
			}
		}

		if (null != document.getDoctype()) {
			reqError = AppStringUtils.validateStr(document.getDoctype(), reqError);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				reqError.setValue(RequestTypes.ErrorCodes.INVALID_DOCTYPE.toString());
				return reqError;
			}
		}
		logger.error(logprefix + " End");

		// String logprefix = "addDocument ";
		// logger.error(logprefix + " Begin");
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		Date ts = new Date();
		document.setUpdatedon(new Timestamp(ts.getTime()));
		// value not use
		Document doc = docDao.add(document);
		logger.error(logprefix + " End");
		// return doc;

		return reqError;
	}

	public ErrorCodes deleteObjectsFromIds(List<Long> ids, Request reqType) {
		String logprefix = "[" + reqType + "] delete List ";
		logger.error(logprefix + " Begin for size =" + ids.size());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		List<Document> dlist = new ArrayList<Document>();
		for (Long id : ids) {
			Document doc = this.getDoc(id);
			dlist.add(doc);
		}

		reqError = this.deleteObjects(dlist, reqType);
		logger.error(logprefix + " End");
		return reqError;
	}

	private ErrorCodes deleteObjects(List<Document> dlist, Request reqType) {
		String logprefix = "[" + reqType + "] delete Doc List ";
		logger.error(logprefix + " Begin for size =" + dlist.size());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		for (Document d : dlist) {
			this.deleteDoc(d);
		}

		// reqError = eventDao.deleteObjects(eventList);
		logger.error(logprefix + " End");
		return reqError;
	}

	@Override
	public ErrorCodes deleteFile(String docpath) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.error("Deleting file:" + docpath);

		if (null != docpath && !(docpath.trim().isEmpty())) {
			// logger.error("Doc path :"+ dbDoc.getDocpath());
			String fullplatformpath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
			logger.error("docPath after fetching cat home:" + fullplatformpath);
			String filepath = UtilFuncs.getFilePath(fullplatformpath, docpath);// dirpath+"/"+caseName;
			logger.error("filepath after append docpath:" + filepath);

			File file = new File(filepath);
			if (file.exists()) {
				if (file.delete()) {
					logger.error(file.getName() + " doc file is deleted!");
				} else {
					logger.error("Delete operation failed for doc file.");
					reqError = ErrorCodes.FAILED_TO_DELETE_DOC_FILE;
					return reqError;
				}
			}
		}

		return reqError;
	}

	@Override
	public String storeFile(MultipartFile file, String dir, String width, String height) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		logger.error("***resizing Image Begain***");
		if (fileName.contains("..")) {
			return ("FAILED" + fileName);
		}
		String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
		String directoryName = UtilFuncs.getFilePath(docPath, dir);
		reqError = UtilFuncs.createRecurrDirs(docPath, dir);
		String UPLOAD_PATH = UtilFuncs.getFilePath(directoryName, fileName);
		//logger.error("UPLOADPATH:"+UPLOAD_PATH);
		int tagWidth = Integer.valueOf(width);
		int tagHeight = Integer.valueOf(height);

		try {
			Image img = ImageIO.read(file.getInputStream());
			//logger.error("Image"+img);
			BufferedImage tempJPG = resizeImage(img, tagWidth, tagHeight);
			//logger.error("BufferedImage"+tempJPG);
			ImageIO.write(tempJPG, "jpg", new File(UPLOAD_PATH));
		} catch (IOException e) {
			logger.error("****resizing Image incomplete*****" , e);
		}

		return "SUCCESS";
	}

	/*@Override
	public String storeFile(MultipartFile file, String dir) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		logger.error("***resizing Image Begain***");
		if (fileName.contains("..")) {
			return ("FAILED" + fileName);
		}
		String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath);
		String directoryName = UtilFuncs.getFilePath(docPath, dir);
		reqError = UtilFuncs.createRecurrDirs(docPath, dir);
		String UPLOAD_PATH = UtilFuncs.getFilePath(directoryName, fileName);
		Image img = null;
		BufferedImage tempJPG = null;
		try {
			img = ImageIO.read(file.getInputStream());
			tempJPG = resizeImage(img, 400, 400);
			ImageIO.write(tempJPG, "jpg", new File(UPLOAD_PATH));
			logger.error("****resizing Image complete*****");
		} catch (IOException e) {
			logger.error("****resizing Image incomplete*****" + e);
		}

		return "SUCCESS";
	}*/
}
