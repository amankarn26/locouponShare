package com.locoupon.common.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.DiscountDao;
import com.locoupon.common.entity.Discount;
import com.locoupon.common.entity.Document;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.DiscountModal;
import com.locoupon.common.model.CustomerType.type;
import com.locoupon.common.service.DiscountService;
import com.locoupon.common.service.DocumentService;
import com.locoupon.common.util.UtilFuncs;

@Service
public class DiscountServiceImpl implements DiscountService {

	private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

	@Autowired
	private DiscountDao discountDao;

	@Autowired
	private DocumentService docServ;

	@Value("${windocpath}")
	private String windocpath;

	@Value("${lindocpath}")
	private String lindocpath;

	@Override
	public List<Discount> getAllValidDiscount() {

		// logger.debug("getAllDiscount Start");
		List<Discount> fList = new ArrayList<Discount>();
		try {
			fList = discountDao.getAllValidDiscount();
		} catch (Exception e) {
			logger.error("Exception in getAllDiscount " + e);
			return null;
		}
		return fList;
	}

	@Override
	public List<Discount> getAllDiscount() {

		// logger.debug("getAllDiscount Start");
		List<Discount> fList = new ArrayList<Discount>();
		try {
			fList = discountDao.getAllDiscount();
		} catch (Exception e) {
			logger.error("Exception in getAllDiscount " + e);
			return null;
		}
		return fList;
	}
	public ErrorCodes addDiscount(Discount cat, Request reqType) {

		// String logprefix = "["+reqType+"] addFirm ";
		// logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		String catname = UtilFuncs.getNormalizeName(cat.getName());
		cat.setName(catname.toUpperCase());
		cat.setCouponcode(cat.getCouponcode().toUpperCase());
		reqError = this.chekcIfDiscountExist(cat, reqError);
		if (reqError == RequestTypes.ErrorCodes.NONE) {
			reqError = discountDao.addDiscount(cat);
			return reqError;
		}
		reqError = RequestTypes.ErrorCodes.DUPLICATE_COUPONCODE;
		 logger.debug(" End");
		return reqError;
	}

	public ErrorCodes chekcIfDiscountExist(Discount cat, ErrorCodes reqError) {

		Discount dbdiscount = discountDao.checkIfDiscountExists(cat.getCouponcode(), reqError);
		if (null != dbdiscount) {
			logger.debug("Duplicate Discount");
			reqError.setValue(reqError.DUPLICATE_DISCOUNT.toString());
			reqError = ErrorCodes.DUPLICATE_DISCOUNT;
			return reqError;
		}

		return reqError;
	}

	public ErrorCodes deleteDiscount(Discount categ, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] deleteDiscount Begin");

		try {
			reqError = discountDao.deleteDiscount(categ);
			logger.debug("[" + reqType + "] deleteDiscount end");
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in deleteDiscount " + e);
			return reqError.ERROR_EXCEPTION;
		}
	}

	@Override
	public Discount getDiscountById(Long id) {
		// logger.debug("getDiscountById Start");
		try {
			Discount cat = discountDao.getDiscountById(id);
			return cat;
		} catch (Exception e) {
			logger.error("Exception in fetching getDiscountById ", e);
			return null;
		}
	}

	@Override
	public ErrorCodes addMultipleDiscount(List<Discount> catlist, Request reqType) {
		String logprefix = "[" + reqType + "] addMultipleDiscount ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		for (Discount cat : catlist) {
			reqError = this.addDiscount(cat, reqType);
		}

		logger.debug(logprefix + " End");
		return reqError;
	}

	@Override
	public Discount validateUpdateDiscount(Discount discount, Request reqType, ErrorCodes reqError) {
		String logprefix = " validateUpdateFirmDiscount ";
		logger.debug(logprefix + " Begin");
		Discount dbobj = discountDao.getDiscountById(discount.getId());

		if (null == dbobj) {
			reqError.setValue(reqError.DISCOUNT_DOES_NOT_EXIST.toString());
			return null;
		}

		if (null != discount.getName()) {
			String catname = UtilFuncs.getNormalizeName(discount.getName());
			dbobj.setName(catname.toUpperCase());

		}
		if(null!=discount.getValidtill())
		{
			dbobj.setValidtill(discount.getValidtill());
		}
		// updating photo
		if (null != discount.getPhoto() && null != discount.getPhoto().getDocname()
				&& !(discount.getPhoto().getDocname().trim().isEmpty())) {
			logger.debug(" Validate incoming photo ");
			try {
				reqError = docServ.validate(discount.getPhoto());
			} catch (Exception e) {
				logger.debug(" Exception in  doc validate", e);
				reqError = reqError.ERROR_EXCEPTION;
				return null;
			}

			logger.debug(" Check if photo exists with dbdiscount");
			if (null == dbobj.getPhoto()) {
				logger.debug("Photo is being added first time" + discount.getPhoto().getDocname());

				try {
					Document doc = docServ.add(discount.getPhoto());
					dbobj.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}
			} else {
				logger.debug("Photo Exists; delete old and add new updateON date" + discount.getPhoto().getDocname());
				Document dbPhoto = dbobj.getPhoto();

				try {
					reqError = docServ.deleteDoc(dbPhoto);
				} catch (Exception e) {
					logger.debug(" Exception in  doc delete", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}

				logger.debug("Deleted old, added new" + discount.getPhoto().getDocname());
				try {
					Document doc = docServ.add(discount.getPhoto());
					dbobj.setPhoto(doc);
				} catch (Exception e) {
					logger.debug(" Exception in  doc add", e);
					reqError = reqError.ERROR_EXCEPTION;
					return null;
				}
			}
		}

		logger.debug(logprefix + " End");
		return dbobj;
	}

	@Override
	public ErrorCodes updateDiscount(Discount discount, Request reqType) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("[" + reqType + "] updateDiscount Begin");
		try {
			if (null != discount) {
				reqError = discountDao.updateDiscount(discount);
			} else {
				logger.debug("updateDiscount is NULL");
			}
		} catch (Exception e) {
			logger.debug(" Exception in  updateDiscount ", e);
		}
		logger.debug("[" + reqType + "] updateDiscount End");
		return reqError;
	}

	@Override
	public boolean checkDiscountInUsed(Long id, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DiscountModal getDiscountForCouponcode(String Couponcode, Double cartval) {
		DiscountModal discount = new DiscountModal();
		try {
			Discount dis = discountDao.getDiscountByCouponCode(Couponcode);
			Date currentdate = UtilFuncs.getCurrentDateOnly();
			discount.setName(dis.getName());
			discount.setCouponcode(dis.getCouponcode());
			discount.setValidtill(dis.getValidtill());
			logger.error("Validtill:"+dis.getValidtill()+":Currentdate:"+currentdate +":cartval:"+cartval+":minvalue:"+dis.getMinvalue());
			
			if(dis.getValidtill().before(currentdate) || cartval < dis.getMinvalue())
			{
				discount.setValid(EnumBoolean.type.NO);	
				discount.setCartvalue(cartval);
				discount.setDiscount(0.0);
				discount.setDisvalue(cartval);
				return discount;
			}else
			{

				discount.setValid(EnumBoolean.type.YES);	
				discount.setCartvalue(cartval);
				discount.setDiscount(dis.getDiscount());
				Double disvalue= UtilFuncs.getPercentValue(dis.getDiscount(), cartval);
				Double value = cartval-disvalue;
				discount.setDisvalue(value);
				
				
			}
		} catch (Exception e) {
			logger.debug("Exception in getDiscountForCouponcode",e);
		}
		return discount;
	}

	@Override
	public List<Discount> getDiscountByType(type type) {

		// logger.debug("getAllDiscount Start");
		List<Discount> fList = new ArrayList<Discount>();
		try {
			fList = discountDao.getDiscountByType(type);
		} catch (Exception e) {
			logger.error("Exception in getDiscountByType " + e);
			return null;
		}
		return fList;
	}

	@Override
	public DiscountModal getDiscountForCouponcode(String Couponcode, Double cartval, type type) {
		DiscountModal discount = new DiscountModal();
		try {
			Discount dis = discountDao.getDiscountByCouponCode(Couponcode,type);
			Date currentdate = UtilFuncs.getCurrentDateOnly();
			discount.setName(dis.getName());
			discount.setCouponcode(dis.getCouponcode());
			discount.setValidtill(dis.getValidtill());
			logger.error("Validtill:"+dis.getValidtill()+":Currentdate:"+currentdate +":cartval:"+cartval+":minvalue:"+dis.getMinvalue());
			
			if(dis.getValidtill().before(currentdate) || cartval < dis.getMinvalue())
			{
				discount.setValid(EnumBoolean.type.NO);	
				discount.setCartvalue(cartval);
				discount.setDiscount(0.0);
				discount.setDisvalue(cartval);
				return discount;
			}else
			{

				discount.setValid(EnumBoolean.type.YES);	
				discount.setCartvalue(cartval);
				discount.setDiscount(dis.getDiscount());
				Double disvalue= UtilFuncs.getPercentValue(dis.getDiscount(), cartval);
				Double value = cartval-disvalue;
				discount.setDisvalue(value);
				
				
			}
		} catch (Exception e) {
			logger.debug("Exception in getDiscountForCouponcode",e);
		}
		return discount;
	}

	@Override
	public List<Discount> getAllValidDiscount(type type) {

		// logger.debug("getAllDiscount Start");
		List<Discount> fList = new ArrayList<Discount>();
		try {
			fList = discountDao.getAllValidDiscount(type);
		} catch (Exception e) {
			logger.error("Exception in getAllValidDiscount type " + e);
			return null;
		}
		return fList;
	}

}
