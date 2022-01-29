package com.locoupon.common.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.ProductDao;
import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.MerchantOffer;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.Folder;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.CompProduct;
import com.locoupon.common.model.CompStock;
import com.locoupon.common.model.DishDetailStats;
import com.locoupon.common.model.ReturnObject;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.service.ProductService;
import com.locoupon.common.service.MerchantOfferService;
import com.locoupon.common.util.UtilFuncs;


@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao prodDao;

	@Autowired
	private DocumentService docServ;

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;

	@Value("${macdocpath}")
	private String macdocpath;

	@Autowired
	private MerchantOfferService stkServ;

	@Override
	public List<Product> getAllProduct() {
		logger.debug("getAllProduct Start");
		List<Product> pList = new ArrayList<Product>();
		try {
			List<Product> aList = prodDao.getAllProduct();
			for (Product prod : aList)
			{
				List<MerchantOffer> mofferlist = stkServ.getAllProdStock(prod.getId());
				prod.setMerchantoffer(mofferlist);
				pList.add(prod);
			}

		} catch (Exception e) {
			logger.error("Exception in getAllProduct " + e);
			return null;
		}
		return pList;
	}

	@Override
	public List<Product> getAllProduct(Integer index, Integer limit) {
		logger.debug("getAllProduct Start");
		List<Product> pList = new ArrayList<Product>();
		try {
			List<Product> aList = prodDao.getAllProduct(index, limit);
			for (Product prod : aList)
			{
				List<MerchantOffer> mofferlist = stkServ.getAllProdStock(prod.getId());
				prod.setMerchantoffer(mofferlist);
				pList.add(prod);
			}

		} catch (Exception e) {
			logger.error("Exception in getAllProduct " + e);
			return null;
		}
		return pList;
	}


	@Override
	public Product getProductByName(String dname) {
		logger.debug("getProductByName Start:"+dname);
		Product dish = null;
		try {
			dish = prodDao.getProductByName(dname);


		} catch (Exception e) {
			logger.error("Exception in getProductByName " + e);
			return null;
		}
		return dish;
	}

	@Override
	public List<Product> getAllProductByProdType(String menutype) {
		logger.debug("getAllProductByProdType Start:"+menutype);
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getAllProduct(menutype);

		} catch (Exception e) {
			logger.error("Exception in getAllProduct " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<Product> getAllProductByCategory(String category) {
		logger.debug("getAllProductByCategory Start:"+category);
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getAllProductByCategory(category);

		} catch (Exception e) {
			logger.error("Exception in getAllProductByCategory " + e);
			return null;
		}
		return aList;
	}


	@Override
	public List<Product> getProductByCategoryAndProdType(String category, String menutype) {
		logger.debug("getProductByCategoryAndProdType Start:"+category+":"+menutype);
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getProductByCategoryAndProdType(category, menutype);

		} catch (Exception e) {
			logger.error("Exception in getProductByCategoryAndProdType " + e);
			return null;
		}
		return aList;
	}

	@Override
	public Product getProductById(Long id) {
		logger.debug("getProductById Start");
		try {
			Product prod = prodDao.getProductById(id);
			List<MerchantOffer> mofferlist = stkServ.getAllProdStock(prod.getId());
				prod.setMerchantoffer(mofferlist);
				
			

			return prod;
		} catch (Exception e) {
			logger.error("Exception in fetching getProductById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes validateAddProduct(Product dish, Request reqType) {
		String logprefix = " validateAddProduct ";
		logger.debug("validateAddProduct Start");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		if (null == dish.getName() || dish.getName().trim().isEmpty()) {
			logger.debug(logprefix + "Failed Name is Null");
			reqError.setValue(ErrorCodes.NAME_IS_NULL.toString());
			reqError = ErrorCodes.NAME_IS_NULL;
			return reqError;
		}



		Product dbprod = checkIfProductExists(dish, reqError);
		if (null != dbprod) {
			logger.debug(logprefix + " Product already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_PRODUCT.toString());
			reqError = ErrorCodes.DUPLICATE_PRODUCT;
			logger.debug(logprefix + " End");
			return reqError;
		}

		logger.debug("validateAddProduct end");
		dish.setName(UtilFuncs.convertToCamelCase(dish.getName()));
		//dish.setVariant(UtilFuncs.convertToCamelCase(dish.getVariant()));
		return reqError;
	}

	@Override
	public ErrorCodes deleteProduct(Product dbproduct, Request reqType) {

		String logprefix = "[" + reqType + "] delete Product ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug(logprefix + " docs Begin");
		List<MerchantOffer> pstocklist = stkServ.getAllProdStock(dbproduct.getId());
		if(pstocklist.size()>0)
		{
			logger.debug("Prodstock found for product:"+dbproduct.getName());
			reqError = RequestTypes.ErrorCodes.PRODUCT_IN_USE;
			return reqError;

		}else
		{

			Set<Document> photos = dbproduct.getPhotos();
			if (photos.size() > 0) {
				for (Document doc : photos) {
					logger.debug(logprefix + " delete doc: " + doc.getDocname());
					try {
						reqError = docServ.deleteDoc(doc);
						logger.debug("deleteDocument End");

					} catch (Exception e) {
						logger.debug("deleteDocument End");
						reqError = ErrorCodes.FAILED_TO_DELETE_DOC;
					}
				}
			}
			logger.debug(logprefix + " docs End");

			logger.debug(logprefix
					+ " deleting dir firmid/Product/Product+id; Product Stock to get deleted with Product Automatically");

			String docPath = UtilFuncs.getPlatformSpecificFileUploadPath(windocpath, lindocpath,macdocpath);
			String subdir = UtilFuncs.concatenateDirs(docPath, "11",
					UtilFuncs.getSubdir(Folder.type.PRODUCT));
			String fullDirPath = UtilFuncs.getDirName(docPath, subdir, dbproduct.getId().toString());
			logger.debug(logprefix + " deleting dir fullDirPath:" + fullDirPath);
			reqError = docServ.deleteDir(fullDirPath);

			logger.debug(logprefix + " Delete Object " + dbproduct.getId());
			reqError = prodDao.deleteProduct(dbproduct);
			logger.debug(logprefix + " End");
			return reqError;
		}

	}

	@Override
	public Product validateUpdateProduct(Product product, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateProduct ";
		logger.debug(logprefix + " Begin");

		Product dbprod = prodDao.getProductById(product.getId());
		if (null == dbprod) {
			reqError.setValue(reqError.PRODUCT_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
			return null;
		}


		if (null != product.getName() && !(product.getName().toString().trim().isEmpty())) {
			dbprod.setName(UtilFuncs.convertToCamelCase(product.getName()));
		}

		if(null!=product.getDetails())
		{
			dbprod.setDetails(product.getDetails());
		}
		if(null!=product.getBrand())
		{
			dbprod.setBrand(product.getBrand());
		}
		if(null!=product.getProdtype())
		{
			dbprod.setProdtype(product.getProdtype());
		}
		if(null!=product.getStatus())
		{
			dbprod.setStatus(product.getStatus());
		}
		if(null!=product.getActualprice())
		{
			dbprod.setActualprice(product.getActualprice());
		}
		if(null!=product.getPointprice())
		{
			dbprod.setPointprice(product.getPointprice());
		}
		
		logger.debug(logprefix + " End");
		return dbprod;
	}

	@Override
	public ErrorCodes updateProduct(Product dbproduct, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateProduct Begin");
		try {
			if (null != dbproduct) {
				reqError = prodDao.updateProduct(dbproduct);
			} else {
				logger.debug("dbproduct is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateProduct ", e);
		}
		logger.debug("[" + reqType + "] updateProduct End");
		return reqError;
	}

	@Override
	public Product validateIfProductExists(Product product, ErrorCodes reqError) {
		String logprefix = " validateIfProductExists ";
		logger.debug(logprefix + " Begin");

		Product dbproduct = prodDao.getProductById(product.getId());
		if (null == dbproduct) {
			reqError.setValue(reqError.PRODUCT_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
			return null;
		}
		return dbproduct;
	}

	public ErrorCodes addPhotosToProduct(Product dbproduct, Set<Document> photos, Request reqType) {
		String logprefix = "[" + reqType + "] addPhotosToProduct ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Set<Document> productPhotos = dbproduct.getPhotos();
		if (null == productPhotos) {
			productPhotos = new HashSet<Document>(0);
		}
		if (null != dbproduct && photos.size() > 0) {
			for (Document photo : photos) {
				reqError = docServ.validate(photo);
				if (reqError != ErrorCodes.NONE) {
					logger.debug("Validation failed for doc " + reqError);
					continue;
				}
				logger.debug("Adding photo to productPhotos: " + photo.getDocname());
				Document dbDoc = docServ.add(photo);
				productPhotos.add(dbDoc);
			}
		}
		logger.debug("Setting productPhotos to product: " + productPhotos.size());

		dbproduct.setPhotos(productPhotos);
		try {
			reqError = prodDao.updateProduct(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}

	public Product validateIfProductExists(Long container, ErrorCodes reqError) {
		String logprefix = " validateIfProductExists ";
		logger.debug(logprefix + " Begin");

		Product dbproduct = prodDao.getProductById(container);
		if (null == dbproduct) {
			reqError.setValue(reqError.PRODUCT_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODUCT_DOES_NOT_EXIST;
			return null;
		}
		return dbproduct;
	}

	public ErrorCodes deleteMultipleProductPhotos(Product dbproduct, List<Long> ids, Request reqType) {
		String logprefix = "[" + reqType + "] deletePhotos ";
		logger.debug(logprefix + " Begin " + ids.size());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		Set<Document> autoPhoto = dbproduct.getPhotos();

		if (autoPhoto.size() > 0) {
			for (Long id : ids) {
				for (Document photo : autoPhoto) {
					if (id.equals(photo.getId())) {
						logger.debug("Found Photo to remove : " + id);
						dbproduct.deletePhotos(photo);
						reqError = docServ.deleteDoc(photo);
						break;
					}
				}
			}
		}

		logger.debug(logprefix + " Product photo size after remove = " + dbproduct.getPhotos().size());
		try {
			reqError = prodDao.updateProduct(dbproduct);
		} catch (Exception e) {
			reqError.setValue(ErrorCodes.ERROR_EXCEPTION.toString());
			reqError = ErrorCodes.ERROR_EXCEPTION;
			e.printStackTrace();
		}
		logger.debug(logprefix + " End");
		return reqError;

	}

	@Override
	public ErrorCodes addProduct(Product product, Request reqType) {

		String logprefix = "[" + reqType + "] addProduct ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = prodDao.add(product);

		logger.debug(logprefix + " End");
		return reqError;
	}

	@Override
	public Product checkIfProductExists(Product product, ErrorCodes reqError) {
		String logprefix = " checkIfProductExists ";
		logger.debug(logprefix + " Begin");
		Product prod = prodDao.checkIfProductExists(product.getName(),reqError);
		logger.debug(logprefix + " End " + "Error: " + reqError);
		return prod;
	}


	@Override
	public DishDetailStats getProductStats(Long id) {
		logger.debug("getProductStats Start:" + id);
		try {
			DishDetailStats pdetail = prodDao.getProductStats(id);
			return pdetail;
		} catch (Exception e) {
			logger.error("Exception in fetching getProductStats ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes addProdType(ProdType menutype, Request reqType) {

		String logprefix = "[" + reqType + "] addProdType ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		menutype.setName(UtilFuncs.convertToCamelCase(menutype.getName()));
		ProdType dbmenutype = prodDao.checkIfProdTypeExist(menutype.getCategory(),menutype.getName());
		if (null != dbmenutype) {
			logger.debug(logprefix + " ProdType already exists ");
			reqError.setValue(ErrorCodes.DUPLICATE_MENUTYPE.toString());
			reqError = ErrorCodes.DUPLICATE_MENUTYPE;
			logger.debug(logprefix + " End");
			return reqError;
		}
		reqError = prodDao.addProdType(menutype);
		logger.debug(logprefix + " End");
		return reqError;
	}

	/*
	 * @Override public ErrorCodes checkIfProductTypeInUse(Long id) { // TODO
	 * Auto-generated method stub return null; }
	 */

	@Override
	public ErrorCodes deleteProdType(ProdType dbobj, Request reqType) {
		String logprefix = "[" + reqType + "] delete ProdType ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = prodDao.deleteProdType(dbobj);
		logger.debug(logprefix + " End");
		return reqError;
	}


	@Override
	public ProdType validateUpdateProdType(ProdType menutype, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateProdType ";
		logger.debug(logprefix + " Begin");

		ProdType dbobj = prodDao.getProdTypeById(menutype.getId());
		if (null == dbobj) {
			reqError.setValue(reqError.PRODTYPE_DOES_NOT_EXIST.toString());
			reqError = RequestTypes.ErrorCodes.PRODTYPE_DOES_NOT_EXIST;
			return null;
		}


		if (null != menutype.getName() && !(menutype.getName().toString().trim().isEmpty())) {
			dbobj.setName(UtilFuncs.convertToCamelCase(menutype.getName()));
		}

		if (null != menutype.getDescription())
		{
			dbobj.setDescription(menutype.getDescription());
		}

		if (null != menutype.getPhoto() && null != menutype.getPhoto().getDocname()
				&& !(menutype.getPhoto().getDocname().trim().isEmpty())) {
			logger.debug(" Validate incoming photo ");
			try {
				reqError = docServ.validate(menutype.getPhoto());
			} catch (Exception e) {
				logger.debug(" Exception in  doc validate", e);
				reqError = ErrorCodes.ERROR_EXCEPTION;
				return null;
			}

			logger.debug(" Check if photo exists with dbfirm");
			if (null == dbobj.getPhoto()) {
				logger.debug("Photo is being added first time" + menutype.getPhoto().getDocname());

				try {
					Document doc = docServ.add(menutype.getPhoto());
					dbobj.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}
			} else {
				logger.debug("Photo Exists; delete old and add new updateON date" + menutype.getPhoto().getDocname());
				Document dbPhoto = dbobj.getPhoto();

				try {
					reqError = docServ.deleteDoc(dbPhoto);
				} catch (Exception e) {
					logger.debug(" Exception in  doc delete", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}

				logger.debug("Deleted old, added new" + menutype.getPhoto().getDocname());
				try {
					Document doc = docServ.add(menutype.getPhoto());
					dbobj.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = ErrorCodes.ERROR_EXCEPTION;
					return null;
				}
			}
		}

		

		logger.debug(logprefix + " End");
		return dbobj;
	}

	@Override
	public ErrorCodes updateProdType(ProdType dbmenu, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateProdType Begin");
		try {
			if (null != dbmenu) {
				reqError = prodDao.updateProdType(dbmenu);
			} else {
				logger.debug("dbmenu is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateProdType ", e);
		}
		logger.debug("[" + reqType + "] updateProdType End");
		return reqError;
	}

	@Override
	public ProdType getProdTypeByName(String name) {
		logger.debug("getProdTypeByName Start");
		List<ProdType> aList = new ArrayList<ProdType>();
		try {
			aList = prodDao.getProdTypeByName(name);
		} catch (Exception e) {
			logger.error("Exception in getProdTypeByName " + e);
			return null;
		}
		return aList.get(0);
	}

	@Override
	public Product getProductByNameAndRateUnit(String name, RateUnit.type unit) {
		logger.debug("getProductByName Start");
		try {
			Product dish = prodDao.getProductByPnameAndRateunit(name, unit);
			return dish;
		} catch (Exception e) {
			logger.error("Exception in fetching getProductByName ", e);
			return null;
		}
	}

	@Override
	public List<ProdType> getAllProdTypeByCategory(String category) {
		logger.debug("getAllProdTypeByCategory Start");
		List<ProdType> aList = new ArrayList<ProdType>();
		try {
			aList = prodDao.getAllProdTypeByCategory(category);
		} catch (Exception e) {
			logger.error("Exception in getAllProdTypeByCategory " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<ProdType> getAllProdType(Integer index, Integer limit) {
		logger.debug("getAllProdType Start");
		List<ProdType> aList = new ArrayList<ProdType>();
		try {
			aList = prodDao.getAllProdType(index, limit);
		} catch (Exception e) {
			logger.error("Exception in getAllProdType " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<ProdType> getAllProdType() {
		logger.debug("getAllProdType Start");
		List<ProdType> aList = new ArrayList<ProdType>();
		try {
			aList = prodDao.getAllProdType();
		} catch (Exception e) {
			logger.error("Exception in getAllProdType " + e);
			return null;
		}
		return aList;
	}

	@Override
	public ProdType getProdTypeById(Long id) {
		logger.debug("getProdTypeById Start");
		try {
			ProdType obj = prodDao.getProdTypeById(id);
			return obj;
		} catch (Exception e) {
			logger.error("Exception in fetching getProdTypeById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes checkIfProdTypeInUse(Long id, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("checkIfProdTypeInUse Start");
		try {
			ProdType obj = prodDao.getProdTypeById(id);
			if(null != obj)
			{
				boolean checkInUse = prodDao.checkIfMenuInUSe(obj.getName());
				if(checkInUse == true)
				{
					return RequestTypes.ErrorCodes.MENUTYPE_IN_USE;
				}
			}
			else
			{
				return RequestTypes.ErrorCodes.PRODTYPE_DOES_NOT_EXIST;
			}
		} catch (Exception e) {
			logger.error("Exception in fetching getProdTypeById ", e);
			return reqError;
		}
		return reqError;
	}

	@Override
	public List<ReturnObject> getAllProdTypeUnique() {
		logger.debug("getAllProdTypeUnique Start");
		List<ReturnObject> aList = new ArrayList<ReturnObject>();
		try {
			List<String> ptypelist = prodDao.getAllProdTypeUnique();
			for (String ptype : ptypelist) {
				ReturnObject robj = new ReturnObject();
				robj.setName(ptype);
				aList.add(robj);
			}
			return aList;
		} catch (Exception e) {
			logger.error("Exception in getAllProdTypeUnique " + e);
			return null;
		}

	}

	@Override
	public Object getAllProductForPopular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProductByCategory(String category, Integer index, Integer limit) {
		logger.debug("getAllProductByCategory Start");
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getAllProductByCategory(category,index,limit);

		} catch (Exception e) {
			logger.error("Exception in getAllProductByCategory " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<Product> getBestSeller(String category) {
		logger.debug("getBestSeller Start");
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getBestSeller(category);

		} catch (Exception e) {
			logger.error("Exception in getBestSeller " + e);
			return null;
		}
		return aList;
	}

	@Override
	public List<Product> getAllProductByProductType(String dishtype) {
		logger.debug("getAllProductByProductType Start");
		List<Product> aList = new ArrayList<Product>();
		try {
			aList = prodDao.getAllProductByProdtype(dishtype);

		} catch (Exception e) {
			logger.error("Exception in getAllProductByProductType " + e);
			return null;
		}
		return aList;
	}

	@Override
	public CompProduct getCompactPStock(Long prod, Long firm) {
		String logprefix = "getCompactPStock";
		logger.debug(logprefix + " Start");
		try {

			CompProduct cp = new CompProduct();
			List<CompStock> stocklist = new ArrayList<CompStock>();

			Product p = prodDao.getProductById(prod);
			if (null != p) {
				cp.setId(p.getId());
				cp.setName(p.getName());
				cp.setProdtype(p.getProdtype());
				if (p.getPhotos().size() > 0) {
					for (Document doc : p.getPhotos()) {
						if (doc.getIsprimary() == EnumBoolean.type.YES) {
							cp.setImage(doc.getDocpath());
						}
					}
				}

				/* TODO: check if available filter required */
				List<MerchantOffer> ps = stkServ.getAllProdStock(prod, firm);
				if (ps != null) {
					for (MerchantOffer stock : ps) {
						CompStock cs = new CompStock();
						cs.setId(stock.getId());
						cs.setGst(stock.getGst());
						cs.setAvailable(stock.getAvailable());
						stocklist.add(cs);
					}
				}
				cp.setPstock(stocklist);

			}
			logger.debug(logprefix + " End");
			return cp;

		} catch (Exception e) {
			logger.debug(" Exception in  getCompactPStock ", e);
		}
		return null;

	}

	@Override
	public CompProduct getCompactPStock(Long prod, Long firm,String doctype) {
		String logprefix = "getCompactPStock";
		logger.debug(logprefix + " Start");
		try {

			CompProduct cp = new CompProduct();
			List<CompStock> stocklist = new ArrayList<CompStock>();

			Product p = prodDao.getProductById(prod);
			if (null != p) {
				cp.setId(p.getId());
				cp.setName(p.getName());
				cp.setProdtype(p.getProdtype());
				if (p.getPhotos().size() > 0) {
					for (Document doc : p.getPhotos()) {
						if (doc.getIsprimary() == EnumBoolean.type.YES && doc.getDoctype()== doctype) {
							cp.setImage(doc.getDocpath());
						}
					}
				}

				/* TODO: check if available filter required */
				List<MerchantOffer> ps = stkServ.getAllProdStock(prod, firm);
				if (ps != null) {
					for (MerchantOffer stock : ps) {
						CompStock cs = new CompStock();
						cs.setId(stock.getId());
						cs.setAvailable(stock.getAvailable());
						stocklist.add(cs);
					}
				}
				cp.setPstock(stocklist);

			}
			logger.debug(logprefix + " End");
			return cp;

		} catch (Exception e) {
			logger.debug(" Exception in  getCompactPStock ", e);
		}
		return null;

	}

	@Override
	public List<CompProduct> getCompactPStock(Long firm, String category) {
		List<CompProduct> compList = new ArrayList<CompProduct>();
		List<Product> prodlist = prodDao.getAllProduct(category);

		if (prodlist.size() > 0) {
			for (Product prod : prodlist) {
				CompProduct cprod = new CompProduct();
				List<CompStock> stocklist = new ArrayList<CompStock>();
				cprod.setId(prod.getId());
				cprod.setName(prod.getName());
				cprod.setProdtype(prod.getProdtype());
				if (prod.getPhotos().size() > 0) {
					for (Document doc : prod.getPhotos()) {
						if (doc.getIsprimary() == EnumBoolean.type.YES) {
							cprod.setImage(doc.getDocpath());
						}
					}
				}
				for (MerchantOffer pstock : stkServ.getAllProdStock(prod.getId(), firm)) {
					CompStock stock = new CompStock();
					stock.setId(pstock.getId());
					stock.setGst(pstock.getGst());
					stock.setAvailable(pstock.getAvailable());
					stocklist.add(stock);
				}

				cprod.setPstock(stocklist);
				compList.add(cprod);
			}
		}
		return compList;
	}

	@Override
	public List<CompProduct> getCompactPStock(Long firm) {
		List<CompProduct> comlist = new ArrayList<CompProduct>();
		List<Long> prodids = stkServ.getAllProdList(firm); // getall product by firm

		if (prodids.size() > 0) {
			for (Long prodid : prodids) {
				Product prod = prodDao.getProductById(prodid);
				CompProduct cfm = new CompProduct();
				List<CompStock> stocklist = new ArrayList<CompStock>();
				cfm.setId(prod.getId());
				cfm.setName(prod.getName());
				cfm.setProdtype(prod.getProdtype());
				if (prod.getPhotos().size() > 0) {
					for (Document doc : prod.getPhotos()) {
						if (doc.getIsprimary() == EnumBoolean.type.YES) {
							cfm.setImage(doc.getDocpath());
						}
					}
				}

				for (MerchantOffer pstock : stkServ.getAllProdStock(prodid, firm)) {
					CompStock cs = new CompStock();
					if(pstock!=null)
					{
						cs.setId(pstock.getId());
						cs.setAvailable(pstock.getAvailable());
					}
					stocklist.add(cs);
				}

				cfm.setPstock(stocklist);
				comlist.add(cfm);
			}
		}

		return comlist;
	}

	@Override
	public List<CompProduct> getCompactPStockByFirmProdType(Long firm, String ptype) {
		List<CompProduct> comlist = new ArrayList<CompProduct>();
		logger.debug("getCompactPStockByFirmProdType");

		for (Product prod : prodDao.getAllProduct(ptype)) {
			CompProduct cfm = new CompProduct();
			List<CompStock> stocklist = new ArrayList<CompStock>();
			cfm.setId(prod.getId());
			cfm.setName(prod.getName());
			cfm.setProdtype(prod.getProdtype());
			if (prod.getPhotos().size() > 0) {
				for (Document doc : prod.getPhotos()) {
					if (doc.getIsprimary() == EnumBoolean.type.YES) {
						//logger.debug("log for check image:" + doc.getId());
						cfm.setImage(doc.getDocpath());
					}
				}
			}
			List<MerchantOffer> pstocklist= stkServ.getAllProdStock(prod.getId(), firm);
			logger.debug("prodstock_size:"+pstocklist.size());
			if(pstocklist.size()>0)
			{

				for (MerchantOffer pstock :pstocklist ) {
					CompStock cs = new CompStock();
					cs.setId(pstock.getId());
					cs.setAvailable(pstock.getAvailable());
					logger.debug("Adding stock to compact");
					stocklist.add(cs);

				}
			}

			cfm.setPstock(stocklist);
			comlist.add(cfm);


		}

		return comlist;
	}

	@Override
	public List<CompProduct> getCompactPStockByProdType( String ptype) {
		List<CompProduct> comlist = new ArrayList<CompProduct>();
		logger.debug("getCompactPStockByProdType");

		for (Product prod : prodDao.getAllProduct(ptype)) {
			CompProduct cfm = new CompProduct();
			List<CompStock> stocklist = new ArrayList<CompStock>();
			cfm.setId(prod.getId());
			cfm.setName(prod.getName());
			cfm.setProdtype(prod.getProdtype());
			if (prod.getPhotos().size() > 0) {
				for (Document doc : prod.getPhotos()) {
					if (doc.getIsprimary() == EnumBoolean.type.YES) {
						//logger.debug("log for check image:" + doc.getId());
						cfm.setImage(doc.getDocpath());
					}
				}
			}
			List<MerchantOffer> pstocklist= stkServ.getAllProdStock(prod.getId());
			logger.debug("prodstock_size:"+pstocklist.size());
			if(pstocklist.size()>0)
			{

				for (MerchantOffer pstock :pstocklist ) {
					CompStock cs = new CompStock();
					cs.setId(pstock.getId());
					cs.setAvailable(pstock.getAvailable());
					logger.debug("Adding stock to compact");
					stocklist.add(cs);

				}
			}

			cfm.setPstock(stocklist);
			comlist.add(cfm);


		}

		return comlist;
	}

	@Override
	public List<CompProduct> getCompactPStock() {
		List<CompProduct> comlist = new ArrayList<CompProduct>();
		List<Long> prodids = stkServ.getAllProdList(); // getall product by firm

		if (prodids.size() > 0) {
			for (Long prodid : prodids) {
				Product prod = prodDao.getProductById(prodid);
				CompProduct cfm = new CompProduct();
				List<CompStock> stocklist = new ArrayList<CompStock>();
				cfm.setId(prod.getId());
				cfm.setName(prod.getName());
				cfm.setProdtype(prod.getProdtype());
				if (prod.getPhotos().size() > 0) {
					for (Document doc : prod.getPhotos()) {
						if (doc.getIsprimary() == EnumBoolean.type.YES) {
							cfm.setImage(doc.getDocpath());
						}
					}
				}

				for (MerchantOffer pstock : stkServ.getAllProdStock(prodid)) {
					CompStock cs = new CompStock();
					if(pstock!=null)
					{
						cs.setId(pstock.getId());
						cs.setAvailable(pstock.getAvailable());
					}
					stocklist.add(cs);
				}

				cfm.setPstock(stocklist);
				comlist.add(cfm);
			}
		}

		return comlist;
	}
}
