package com.locoupon.common.rest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.entity.ProductReview;
import com.locoupon.common.enums.RequestState;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.OneToManyRequest;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.MerchantOfferService;
import com.locoupon.common.service.ProductService;
import com.locoupon.common.service.ReviewService;
import com.locoupon.common.util.UtilFuncs;


@RestController
@RequestMapping(value = "/product", produces = "application/json")
public class ProductRestService {

	private static final Logger logger = LoggerFactory.getLogger(ProductRestService.class);

	@Autowired
	private ProductService ProductServ;
	
	@Autowired
	private ReviewService reviewServ;
	
	@Autowired
	private MerchantOfferService mofferServ;
	

	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getAllProduct( @ModelAttribute("category") String category, @ModelAttribute("mtype") String ptype,
			@ModelAttribute("index") String sindex, @ModelAttribute("limit") String slimit,@ModelAttribute("Producttype") String sProducttype) {
		
		logger.debug("getAllProduct");

		List<Product> aList = new ArrayList<Product>();
		try {
			if(!category.trim().isEmpty() && sindex.trim().isEmpty() && slimit.trim().isEmpty())
			{
				if(!ptype.trim().isEmpty())
				{
				aList = ProductServ.getProductByCategoryAndProdType(category, ptype);
				}else
				{
				aList = ProductServ.getAllProductByCategory(category);
				}
			}
			else if(!category.trim().isEmpty() && !sindex.trim().isEmpty() && !slimit.trim().isEmpty()) {

				Integer index = UtilFuncs.getIntFromStr(sindex);
				Integer limit = UtilFuncs.getIntFromStr(slimit);
				aList = ProductServ.getAllProductByCategory(category, index, limit);
			}
			else if(!ptype.trim().isEmpty())
			{
				aList = ProductServ.getAllProductByProdType(ptype);
			}
			
			else if(!sindex.trim().isEmpty() && !slimit.trim().isEmpty())
			{
				Integer index = UtilFuncs.getIntFromStr(sindex);
				Integer limit = UtilFuncs.getIntFromStr(slimit);
				aList = ProductServ.getAllProduct(index, limit);
			}
			else
			{
				aList = ProductServ.getAllProduct();
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllProduct ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity searchProduct(@ModelAttribute("ProdType") String ProdType,
			@ModelAttribute("name") String sname) {

		logger.debug("searchProduct");
		List<Product> aList = new ArrayList<Product>();
		try {
			if (!ProdType.trim().isEmpty()) {
				aList = ProductServ.getAllProductByProdType(ProdType);
			} else if (!sname.trim().isEmpty()) {
				Product Product = ProductServ.getProductByName(sname);
				return new ResponseEntity(Product, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllProduct ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.GET)
	public ResponseEntity getProductById(@PathVariable(value = "param") Long id) {

		//logger.debug("getProductById: " + id);
		if (null == id) {
			logger.error("getProductById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(ProductServ.getProductById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getProductById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity addProduct(@RequestBody Product product) {

		//logger.debug("addProduct Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {
			reqError = ProductServ.validateAddProduct(product, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("validateAddProduct End With :" + reqError);
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}
			//product.setName(product.getName().replaceAll("\\s+", " "));
			reqError = ProductServ.addProduct(product, reqType);
			if (reqError == RequestTypes.ErrorCodes.NONE) {
				logger.debug("addProduct End");
				ReturnObject obj = new ReturnObject();
				obj.setId(product.getId());
				return new ResponseEntity(obj, HttpStatus.OK);
			}
			logger.debug("addProduct End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addProduct " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \" Product\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleproduct(@RequestBody List<Product> plist) {

		logger.debug("addProduct Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		for (Product prod : plist) {
			reqError = ProductServ.validateAddProduct(prod, reqType);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("validateAddProduct End With :" + reqError);
//				return new ResponseEntity(reqError, HttpStatus.OK);
			}

			try {

				reqError = ProductServ.addProduct(prod, reqType);
				if (reqError != RequestTypes.ErrorCodes.NONE) {
					logger.debug("Error in add Product");
					prod.setReqstatus(RequestState.state.FAILED.toString());
					prod.setErrCode(reqError);
					continue;
				}
				prod.setReqstatus(RequestState.state.SUCCESS.toString());
				prod.setErrCode(reqError);

			} catch (Exception e) {
				logger.debug("Exception in add multiple product ", e);
				prod.setReqstatus(RequestState.state.FAILED.toString());
				prod.setErrCode(reqError);
				continue;
			}

		}
		return new ResponseEntity(plist, HttpStatus.OK);
	}

	@RequestMapping(value = "/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteProduct(@PathVariable(value = "param") Long id) {

		logger.debug("deleteProduct Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Product dbproduct = null;
		try {
//			dbproduct = prodServ.getProductById(id);
			dbproduct = ProductServ.getProductById(id);
			if (null == dbproduct) {
				logger.debug("deleteProduct product stock exist:" + reqError.toString());
				return new ResponseEntity(reqError.PRODUCT_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
			}

			reqError = ProductServ.deleteProduct(dbproduct, reqType);
			logger.debug("deleteProduct End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteProduct End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity updateProduct(@RequestBody Product product) {

		//logger.debug("updateProduct Begin ");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;
		if (product.getId() == null) {
			return new ResponseEntity(reqError.INPUT_IS_NULL, HttpStatus.OK);
		}

		Product dbproduct = null;
		try {
			dbproduct = ProductServ.validateUpdateProduct(product, reqType, reqError);
			if (dbproduct == null || reqError != RequestTypes.ErrorCodes.NONE) {
				logger.debug("Faild :-Valied Product update  End");
				return new ResponseEntity(reqError.PRODUCT_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
			}

			reqError = ProductServ.updateProduct(dbproduct, reqType);
			logger.debug("updateProduct End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateProduct " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"FAILD TO Product ADD\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/photos", method = RequestMethod.POST)
	public ResponseEntity addPhotosToProduct(@RequestBody Product product) {

		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		if (product.getPhotos().size() <= 0) {
			return new ResponseEntity(reqError.REQUIRED_INPUT_NULL, HttpStatus.OK);
		}
		logger.debug("addPhotosToProduct Begin");
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		Product dbproduct = null;
		try {
			dbproduct = ProductServ.getProductById(product.getId());
			if (null == dbproduct) {
				reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
				return new ResponseEntity(reqError, HttpStatus.OK);
			}

			reqError = ProductServ.addPhotosToProduct(dbproduct, product.getPhotos(), reqType);
			logger.debug("addPhotosToProduct End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in addPhotosToProduct: " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE \"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/photos/deleteM", method = RequestMethod.POST)
	public ResponseEntity deleteMultipleProductPhotos(@RequestBody OneToManyRequest productPhotos) {

		//logger.debug("deleteMultipleProductPhotos Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;

		Product dbproduct = null;

		try {

			dbproduct = ProductServ.getProductById(productPhotos.getContainer());
			if (null == dbproduct) {
				reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
				return new ResponseEntity(reqError.errordesc(), HttpStatus.OK);
			}

			reqError = ProductServ.deleteMultipleProductPhotos(dbproduct, productPhotos.getIds(), reqType);
			logger.debug("deleteMultipleProductPhotos End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteMultipleProductPhotos End ", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/bestseller", method = RequestMethod.GET)
	public ResponseEntity getBestSeller(@ModelAttribute("category") String category) {
		logger.debug("getBestSeller");
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = ProductServ.getBestSeller(category);
			return new ResponseEntity(aList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getBestSeller: " + e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	// ************************Menu Type*******************************

	@RequestMapping(value = "/prodtype", method = RequestMethod.GET)
	public ResponseEntity getProdType(@ModelAttribute("category") String category, @ModelAttribute("index") String sindex,
			@ModelAttribute("limit") String slimit) {

		//logger.debug("getAllProdType");
		List<ProdType> aList = new ArrayList<ProdType>();
		try {
			if (!category.trim().isEmpty()) {
				aList = ProductServ.getAllProdTypeByCategory(category);
			} else if (!sindex.trim().isEmpty() && !slimit.trim().isEmpty()) {
				Integer index = UtilFuncs.getIntFromStr(sindex);
				Integer limit = UtilFuncs.getIntFromStr(slimit);
				aList = ProductServ.getAllProdType(index, limit);
			} else {
				aList = ProductServ.getAllProdType();
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllProdType ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/prodtype/name", method = RequestMethod.GET)
	public ResponseEntity getProdTypeUnique() {
		logger.debug("getProdTypeUnique");
		List<ReturnObject> aList = new ArrayList<ReturnObject>();
		try {
			aList = ProductServ.getAllProdTypeUnique();
			return new ResponseEntity(aList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getProdTypeUnique: " + e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/prodtype/{param}", method = RequestMethod.GET)
	public ResponseEntity getProdTypeById(@PathVariable(value = "param") Long id) {

		//logger.debug("getProdTypeById: " + id);
		if (null == id) {
			logger.error("getProdTypeById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(ProductServ.getProdTypeById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getProdTypeById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/prodtype", method = RequestMethod.POST)
	public ResponseEntity addProdType(@RequestBody ProdType product) {

	///	logger.debug("addProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {
			reqError = ProductServ.addProdType(product, reqType);
			if (reqError == RequestTypes.ErrorCodes.NONE) {
			//	logger.debug("addProdType End");
				ReturnObject obj = new ReturnObject();
				obj.setId(product.getId());
				return new ResponseEntity(obj, HttpStatus.OK);
			}
			//logger.debug("addProdType End");
			return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.debug("Exception in addProdType " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \" Product\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/prodtype/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleProdType(@RequestBody List<ProdType> plist) {

		//logger.debug("addProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		for (ProdType prodstock : plist) {
			try {
				reqError = ProductServ.addProdType(prodstock, reqType);
				if (reqError != RequestTypes.ErrorCodes.NONE) {
					logger.debug("Error in add Product stock");
					continue;
				}

			} catch (Exception e) {
				logger.debug("Exception in add multiple  ", e);
				continue;
			}
		}
		return new ResponseEntity(plist, HttpStatus.OK);
	}

	@RequestMapping(value = "/prodtype/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteProdType(@PathVariable(value = "param") Long id) {

		logger.debug("deleteProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;
		try {
			reqError = ProductServ.checkIfProdTypeInUse(id, reqType);
			if(reqError != RequestTypes.ErrorCodes.NONE)
			{
				logger.debug("deleteProdType End" +reqError);
				return new ResponseEntity(reqError.PRODUCT_IN_USE, HttpStatus.BAD_REQUEST);
			}
			ProdType dbmenu = ProductServ.getProdTypeById(id);
			reqError = ProductServ.deleteProdType(dbmenu, reqType);
			logger.debug("deleteProdType End");
			return new ResponseEntity(reqError, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteProdType End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/prodtype/update", method = RequestMethod.POST)
	public ResponseEntity updateProdType(@RequestBody ProdType menu) {

	//	logger.debug("updateProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		ProdType dbmenu = null;
		try {
			dbmenu = ProductServ.validateUpdateProdType(menu, reqType, reqError);
			if (reqError != RequestTypes.ErrorCodes.NONE) {
				return new ResponseEntity(reqError.toString(), HttpStatus.OK);
			}

			reqError = ProductServ.updateProdType(dbmenu, reqType);
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.debug("Exception in UpdateProdType " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	
	// *************************************************************Product_REview******************************************************************************************

	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public ResponseEntity getReview(@ModelAttribute("product") String sProduct, @ModelAttribute("customer") String scust,
			@ModelAttribute("limit") String slimit, @ModelAttribute("order") String sorder) {

		//logger.debug("getAllProdType");
		List<ProductReview> aList = new ArrayList<ProductReview>();
		try {
			if (!sProduct.trim().isEmpty()) {
				Long Product = UtilFuncs.getLongfromStr(sProduct);
				aList = reviewServ.getReviewForDish(Product);
			} else if(!scust.trim().isEmpty())
			{
				Long cust = UtilFuncs.getLongfromStr(scust);
				aList = reviewServ.getReviewForCustomer(cust);
			}else if(!sorder.trim().isEmpty())
			{
				Long order = UtilFuncs.getLongfromStr(sorder);
				aList = reviewServ.getReviewForOrder(order);
			}
				else {
				aList = reviewServ.getAllReview();
			}
		} catch (Exception e) {
			logger.error(" Exception in getAllReview ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/review/{param}", method = RequestMethod.GET)
	public ResponseEntity getReviewById(@PathVariable(value = "param") Long id) {

		//logger.debug("getProdTypeById: " + id);
		if (null == id) {
			logger.error("getReviewById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.OK);
		}
		try {
			return new ResponseEntity(reviewServ.getReviewById(id), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getReviewById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public ResponseEntity addReview(@RequestBody ProductReview product) {

	///	logger.debug("addProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		try {
			reqError = reviewServ.addReview(product);
			if (reqError == RequestTypes.ErrorCodes.NONE) {
			//	logger.debug("addProdType End");
				ReturnObject obj = new ReturnObject();
				obj.setId(product.getId());
				return new ResponseEntity(obj, HttpStatus.OK);
			}
			//logger.debug("addProdType End");
			return new ResponseEntity(reqError, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in ReviewAdd " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \" Product\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/review/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleReview(@RequestBody List<ProductReview> plist) {

		//logger.debug("addProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.ADD;

		for (ProductReview prodstock : plist) {
			try {
				reqError = reviewServ.addReview(prodstock);
				if (reqError != RequestTypes.ErrorCodes.NONE) {
					logger.debug("Error in add Product stock");
					continue;
				}

			} catch (Exception e) {
				logger.debug("Exception in add multiple  ", e);
				continue;
			}
		}
		return new ResponseEntity(plist, HttpStatus.OK);
	}

	@RequestMapping(value = "/review/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteReview(@PathVariable(value = "param") Long id) {

		logger.debug("deleteReview Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.DELETE;
		try {
			
			ProductReview dbmenu = reviewServ.getReviewById(id);
			if(reqError != RequestTypes.ErrorCodes.NONE)
			{
				logger.debug("deleteReview End" +reqError);
				return new ResponseEntity(reqError.MENUTYPE_IN_USE, HttpStatus.BAD_REQUEST);
			}
			reqError = reviewServ.deleteReview(dbmenu);
			logger.debug("deleteReview End");
			return new ResponseEntity(reqError, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteReview End", e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/review/update", method = RequestMethod.POST)
	public ResponseEntity updateReview(@RequestBody ProductReview menu) {

	//	logger.debug("updateProdType Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		ProductReview dbmenu = null;
		try {
			dbmenu = reviewServ.validateUpdate(menu, reqType, reqError);
			if (dbmenu== null) {
				return new ResponseEntity(reqError, HttpStatus.BAD_REQUEST);
			}

			reqError = reviewServ.updateReview(dbmenu);
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);

		} catch (Exception e) {
			logger.debug("Exception in updateReview " + e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.OK);
			}
			return new ResponseEntity(reqError.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}

	}

	/*********************************************
	 * Merchnat Offer
	 *********************************************/

	@RequestMapping(value = "/moffer", method = RequestMethod.GET)
	public ResponseEntity getAllProdStock(@ModelAttribute("merchant") String smerchant,
			@ModelAttribute("product") String sproduct, @ModelAttribute("index") String sindex,
			@ModelAttribute("soldas") String soldas, @ModelAttribute("limit") String slimit,
			@ModelAttribute("brand") String brand, @ModelAttribute("variant") String variant) {

		// logger.debug("getAllProdStock:" + sproduct);
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		List<MerchantOffer> aList = new ArrayList<MerchantOffer>();
		MerchantOffer stock = new MerchantOffer();

		try {
			
			if(!smerchant.trim().isEmpty())
			{
			Long merchant= UtilFuncs.getLongfromStr(smerchant);
				aList = mofferServ.getProdStockByMerchant(merchant);
			}else if(!sproduct.trim().isEmpty())
			{
				Long product = UtilFuncs.getLongfromStr(sproduct);
				aList = mofferServ.getAllProdStock(product);
			}
			
			
					
				
		} catch (Exception e) {
			logger.error(" Exception in getAllProdStock ", e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(aList, HttpStatus.OK);
	}

	@RequestMapping(value = "/moffer/{param}", method = RequestMethod.GET)
	public ResponseEntity getProdStockById(@PathVariable(value = "param") Long id) {

		// logger.debug("getProdStockById: " + id);
		if (null == id) {
			logger.error("getProdStockById id is NULL");
			return new ResponseEntity(RequestTypes.ErrorCodes.INVALID_INPUT, HttpStatus.BAD_REQUEST);
		}
		try {
			
			MerchantOffer prodstk = mofferServ.getProdStockById(id);
			return new ResponseEntity(prodstk, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception in getProdStockById: " + id, e);
			return new ResponseEntity(RequestTypes.ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/moffer/{param}", method = RequestMethod.DELETE)
	public ResponseEntity deleteProd_Stock(@PathVariable(value = "param") Long id) {

		// logger.debug("deleteProd_Stock Begin");
		RequestTypes.Request reqType = RequestTypes.Request.ADD;
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		MerchantOffer dbprodrl = null;

		dbprodrl = mofferServ.getProdStockById(id);
		if (null == dbprodrl) {
			logger.debug("Product_stock Not Availabel");
			return new ResponseEntity(reqError.PROD_STOCK_DOES_NOT_EXIST, HttpStatus.OK);
		}

		//Product dbprod = prodServ.getProductById(dbprodrl.getProduct());
		try {
			reqError = mofferServ.deleteProdStock(dbprodrl, reqType);
			// logger.debug("deleteProd_Stock End");
			return new ResponseEntity("{\"Status\": \"SUCCESS\"}", HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in deleteProd_Stock End" + e);
			return new ResponseEntity("{\"Status\": \"FAILED\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/moffer/update", method = RequestMethod.POST)
	public ResponseEntity updateProd_Stock(@RequestBody MerchantOffer prodrl) {

		// logger.debug("updateProdStock Begin ");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		RequestTypes.Request reqType = RequestTypes.Request.UPDATE;

		MerchantOffer dbprodrl = null;
		try {
			dbprodrl = mofferServ.validateUpdateProdStock(prodrl, reqType, reqError);
			if (dbprodrl.getErrCode() != RequestTypes.ErrorCodes.NONE) {
				// logger.debug("validateProdStock End");
				return new ResponseEntity(dbprodrl.getErrCode(), HttpStatus.BAD_REQUEST);
			}

			reqError = mofferServ.updateProdStock(dbprodrl, reqType);
			// logger.debug("updateProdStock End");
			return new ResponseEntity(dbprodrl, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception in updateProdStock ", e);
			if (e instanceof SQLException) {
				return new ResponseEntity("{\"Status\": \"DUPLICATE\"}", HttpStatus.OK);
			}
			return new ResponseEntity(ErrorCodes.ERROR_EXCEPTION, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/moffer/addM", method = RequestMethod.POST)
	public ResponseEntity addMultipleStock(@RequestBody List<MerchantOffer> plist) {
		// logger.debug("addMultipleStock Begin");
		RequestTypes.Request reqType = RequestTypes.Request.ADD;
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		for (MerchantOffer prodstock : plist) {
			try {
				reqError = mofferServ.addProdStock(prodstock, reqType);
				if (reqError != RequestTypes.ErrorCodes.NONE) {
					logger.debug("Error in add Product stock");
					prodstock.setReqstatus(RequestState.state.FAILED.toString());
					prodstock.setErrCode(reqError);
					continue;
				}
				prodstock.setReqstatus(RequestState.state.SUCCESS.toString());
				prodstock.setErrCode(reqError);

			} catch (Exception e) {
				logger.debug("Exception in add multiple prodct stock ", e);
				prodstock.setReqstatus(RequestState.state.FAILED.toString());
				prodstock.setErrCode(reqError);
				continue;
			}

		}
		return new ResponseEntity(plist, HttpStatus.OK);
	}

	
	
}