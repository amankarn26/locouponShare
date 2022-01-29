package com.locoupon.common.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.locoupon.common.entity.Address;
import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.RequestState;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.AddressService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.util.UtilFuncs;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

@RestController
@RequestMapping(value = "/common", produces = "application/json")
public class CommonRestService {

	private static final Logger logger = LoggerFactory.getLogger(CommonRestService.class);

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;

	@Value("${outputpath}")
	private String propoutPath;
	
	@Value("${macdocpath}")
	private String macdocpath;

	
	@Autowired
	private DocumentService docServ;


	@Autowired
	private AddressService addressServ;


	@RequestMapping(value = "/doc", method = RequestMethod.GET)
	public ResponseEntity<Document> getAllDocuments(@ModelAttribute(value = "type") String type) {

		// logger.debug("getAllDocuments");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		try {
			if (null == type && (type.trim().isEmpty())) {
				return new ResponseEntity(docServ.getAll(), HttpStatus.OK);
			} else {
				return new ResponseEntity(docServ.getAllDocsOfType(type), HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllDocuments", e);
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doc/getByPath", method = RequestMethod.GET)
	public ResponseEntity<Document> getDocument(@ModelAttribute(value = "path") String path) {

		// logger.debug("getDocument: " + path);
		try {
			return new ResponseEntity(docServ.getDocByPath(path), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getDocument: " + path, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/doc/{param}", method = RequestMethod.GET)
	public ResponseEntity getDocById(@PathVariable(value = "param") Long id) {
		// logger.debug("getDocById: " + id);
		try {
			return new ResponseEntity(docServ.getDoc(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getDocById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doc", method = RequestMethod.POST)
	public ResponseEntity addDocument(@RequestBody Document doc) {

		// logger.debug("addDocument Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		try {
			reqError = docServ.validate(doc);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("addDocument End");
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			Document addDoc = docServ.add(doc);
			logger.debug("addDocument End:" + addDoc.getDocname());
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addDocument " + e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doc/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteDocument(@PathVariable(value = "param") Long id) {

		// logger.debug("deleteDocument Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		Document dbDoc = null;

		try {
			dbDoc = docServ.getDoc(id);
			if (dbDoc == null) {
				logger.debug("deleteDocument End");
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = docServ.deleteDoc(dbDoc);
			logger.debug("deleteDocument End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteDocument End" + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/file/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteFile(@PathVariable(value = "param") String docpath) {

		// logger.debug("deleteFile Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		try {

			reqError = docServ.deleteFile(docpath);
			logger.debug("deleteFile End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteFile End" + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doc/update", method = RequestMethod.POST)
	public ResponseEntity<Document> updateDocument(@RequestBody Document doc) {

		// logger.debug("updateDocument Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Document dbDoc = null;
		try {
			dbDoc = docServ.validateUpdateDoc(doc, reqError);
			if (dbDoc == null) {
				logger.debug("updateDocument End");
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = docServ.update(dbDoc);
			logger.debug("updateDocument End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateDocument End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/doc/upload", method = RequestMethod.POST)
	public ResponseEntity uploadFile(@RequestParam("name") String dir, @RequestParam("doc") MultipartFile file,
			@RequestParam("width") String width, @RequestParam("height") String height) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		String fileName = null;
		if (!width.trim().isEmpty() && !height.trim().isEmpty()) {
			logger.debug("REM : dir name:"+dir);
			fileName = docServ.storeFile(file, dir, width, height);
		} else {
			return new ResponseEntity("{\"Status\": \"EMPTY\"}", HttpStatus.BAD_REQUEST);
		}

		if (fileName != "SUCCESS") {
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/doc/uploadwithjson", method = RequestMethod.POST)
	public ResponseEntity uploadFileAndJson(@FormDataParam("name") String dir,
			@FormDataParam("doc") FormDataBodyPart jsonPart, @FormDataParam("file") InputStream fis,
			@FormDataParam("file") FormDataContentDisposition filecontent) {

		logger.debug("***UPLOAD Doc File ****");

		String fileName = filecontent.getFileName();

		if ((null == fileName) || (fileName.trim().isEmpty())) {
			logger.debug("Filename is null, cannot upload");
			return new ResponseEntity("FILE NAME IS NULL or BLANK", HttpStatus.OK);
		}

		String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
		logger.debug("docPath:" + docPath);
		String directoryName = UtilFuncs.getFilePath(docPath, dir);// dirpath+"/"+caseName;
		// logger.debug("Create directory if it does not exist:" + dir);
		// logger.debug("Directory path:" + directoryName);

		// Create directory if it does not exist
		File directory = new File(String.valueOf(directoryName));
		if (!directory.exists()) {
			logger.debug("Directory does not exist- create new directory");
			directory.mkdir();
			System.out.println("Directory created sucessfully");
			logger.debug("Directory created sucessfully");
			// If you require it to make the entire directory path including parents,
			// use directory.mkdirs(); here instead.
		}
		// logger.debug("File name:" + fileName);
		String UPLOAD_PATH = UtilFuncs.getFilePath(directoryName, fileName);// directoryName+"/"+fileName ;
		logger.debug("UPLOAD PATH:" + UPLOAD_PATH);
		byte[] buffer = new byte[8 * 1024];

		// Now create the output file on the server.
		File outputFile = new File(UPLOAD_PATH);
		// logger.debug("file got created:" + outputFile.getName());
		logger.debug("File saved to server location : " + UPLOAD_PATH);

		if (outputFile.exists()) {
			logger.debug("file already exist, overwriting");
		}

		FileOutputStream writer = null;

		try {
			logger.debug("Creating file:" + outputFile.getName());
			outputFile.createNewFile();

			// Create writer for 'outputFile' to write data read from

			logger.debug("***Writing to file ***");
			writer = new FileOutputStream(outputFile, false);
			int bytesRead = 0;
			while ((bytesRead = fis.read(buffer)) != -1) {
				writer.write(buffer, 0, bytesRead);
			}
			logger.debug("****Writing to file complete*****");
		} catch (IOException e) {
			logger.debug("IOException :" + e);
			e.printStackTrace();
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		} finally {
			try {
				logger.debug("Closing writer");
				if (null != writer) {
					writer.close();
				}
			} catch (IOException e) {
				logger.debug("IOException in closing writer", e);
				e.printStackTrace();
				return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
			}
		}

//		jsonPart.setMediaType(MediaType.APPLICATION_JSON);
		Document fileDoc = jsonPart.getValueAs(Document.class);

		// logger.debug("Adding Doc now " + fileDoc.getDocpath());

		RequestTypes.ErrorCodes reqError = docServ.validateAndAdd(fileDoc);

		if (reqError != RequestTypes.ErrorCodes.NONE) {
			logger.debug("Failed to add doc delete" + fileDoc.getDocpath());
			if (outputFile.delete()) {
				logger.debug(outputFile.getName() + " doc file is deleted!");
			}

			return new ResponseEntity("UPLOAD FAILED", HttpStatus.OK);
		}
		return new ResponseEntity("UPLOAD SUCCESS", HttpStatus.OK);
	}

	@RequestMapping(value = "/doc/multiupload", method = RequestMethod.POST)
	public ResponseEntity multipleUploadFile(@RequestParam("dir") String dir,
			@RequestParam("files") MultipartFile[] files, @RequestParam("width") String width,
			@RequestParam("height") String height) {
		logger.debug("***UPLOAD Doc File ****");
		String fileName = null;
		for (MultipartFile file : files) {
			fileName = docServ.storeFile(file, dir, width, height);
		}
		if (fileName != "SUCCESS") {
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/doc/test", method = RequestMethod.GET)
	public ResponseEntity createRecurDir(@ModelAttribute("dirs") String dirs) {
		// logger.debug("createRecurDir");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
		logger.debug("docPath:" + docPath);
		reqError = UtilFuncs.createRecurrDirs(docPath, dirs);

		return new ResponseEntity(reqError, HttpStatus.OK);
	}

	@RequestMapping(value = "/doc/deleteM", method = RequestMethod.POST)
	public ResponseEntity<Document> deleteMultipleDocuments(@RequestBody List<Long> ids) {

		// logger.debug("delete Multiple Documents Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;
		try {
			reqError = docServ.deleteObjectsFromIds(ids, reqType);
			logger.debug("delete Multiple Documents End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in delete Multiple Documents End: ", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	

	/*********************************************
	 * Address
	 ********************************************************/

	@RequestMapping(value = "/address/update", method = RequestMethod.POST)
	public ResponseEntity updateAddress(@RequestBody Address addr) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		try {
			Address dbaddr = addressServ.update(addr.getId(), addr);

			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateAddress " + e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/numdays", method = RequestMethod.GET)
	public ResponseEntity getDaysBetweenDates(@ModelAttribute("fromdate") String sfromdate,
			@ModelAttribute("todate") String stodate) {

		// logger.debug("getDaysBetweenDates: ");

		Date fromdate = UtilFuncs.getDatefromString(sfromdate);
		Date todate = UtilFuncs.getDatefromString(stodate);

		try {
			long numdays = UtilFuncs.getDaysBetweenDates(fromdate, todate);
			JSONObject json = new JSONObject();
			// logger.debug("getDaysBetweenDates: " + numdays);
			json.put("numdays", numdays);
			return new ResponseEntity(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getDaysBetweenDates: ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/futuredate", method = RequestMethod.GET)
	public ResponseEntity getFutureDate(@ModelAttribute("numdays") String snumdays) {

		// logger.debug("getFutureDate: " + snumdays);
		try {
			Integer numdays = Integer.parseInt(snumdays);
			Date futuredate = UtilFuncs.getFutureDate(numdays);
			Timestamp ts = new Timestamp(futuredate.getTime());
			JSONObject json = new JSONObject();
			logger.debug("getFutureDate: " + ts.toString());
			json.put("futuredate", ts.toString());
			return new ResponseEntity(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getFutureDate: " + snumdays, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public ResponseEntity getFirstAndCurrentDate(@ModelAttribute("numdays") String snumdays) {

		// logger.debug("getFutureDate: " + snumdays);
		try {

			Date firstdate = UtilFuncs.getCurrMonthStart();
			Date currentdate = UtilFuncs.getCurrentDate();
			Timestamp ts = new Timestamp(firstdate.getTime());
			Timestamp current = new Timestamp(currentdate.getTime());
			JSONObject json = new JSONObject();
			logger.debug("getFutureDate: " + ts.toString());
			json.put("firstdate", ts.toString());
			json.put("currentdate", current.toString());
			return new ResponseEntity(json, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getFutureDate: " + snumdays, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}


	
	
	@RequestMapping(value = "/slider/delete", method = RequestMethod.POST)
	public ResponseEntity deleteMultipleSliders(@RequestBody List<Long> ids) {

		logger.debug("deleteMultipleSliders Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {
			reqError = docServ.deleteObjectsFromIds(ids,reqType);
			logger.debug("deleteMultipleSliders End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addArea " + e);

			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}
	
	

}