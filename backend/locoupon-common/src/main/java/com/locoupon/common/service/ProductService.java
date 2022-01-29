package com.locoupon.common.service;

import java.util.List;
import java.util.Set;

import com.locoupon.common.entity.Document;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.model.CompObj;
import com.locoupon.common.model.CompProduct;
import com.locoupon.common.model.DishDetailStats;
import com.locoupon.common.model.ReturnObject;


public interface ProductService {

	List<Product> getAllProduct();
	
	List<Product> getAllProduct(Integer index, Integer limit);
	
	List<Product> getAllProductByProductType(String dishtype);

	Product getProductById(Long id);
	
	List<Product> getAllProductByProdType(String ptype);

	List<Product> getAllProductByCategory(String category);
	
	List<Product> getProductByCategoryAndProdType(String category, String prodtype);

	ErrorCodes validateAddProduct(Product product, Request reqType);

	ErrorCodes addProduct(Product product, Request reqType);

	ErrorCodes deleteProduct(Product dbproduuct, Request reqType);

	Product validateUpdateProduct(Product product, Request reqType, ErrorCodes reqError);

	ErrorCodes updateProduct(Product dbproduct, Request reqType);

	Product validateIfProductExists(Product product, ErrorCodes reqError);

	ErrorCodes addPhotosToProduct(Product dbproduct, Set<Document> photos, Request reqType);

	Product validateIfProductExists(Long container, ErrorCodes reqError);

	ErrorCodes deleteMultipleProductPhotos(Product dbproduct, List<Long> ids, Request reqType);

	Product checkIfProductExists(Product product, ErrorCodes reqError);

	DishDetailStats getProductStats(Long id);

	ErrorCodes deleteProdType(ProdType dbproduct, Request reqType);

	ProdType validateUpdateProdType(ProdType firm, Request reqType, ErrorCodes reqError);

	ErrorCodes updateProdType(ProdType dbfirm, Request reqType);

	ProdType getProdTypeByName(String prodtype);

	Product getProductByNameAndRateUnit(String name, RateUnit.type unit);
	
	List<ProdType> getAllProdTypeByCategory(String category);

	List<ProdType> getAllProdType(Integer index, Integer limit);

	List<ProdType> getAllProdType();

	ProdType getProdTypeById(Long id);

	ErrorCodes addProdType(ProdType product, Request reqType);

	ErrorCodes checkIfProdTypeInUse(Long id, Request reqType);

	List<ReturnObject> getAllProdTypeUnique();

	Product getProductByName(String dname);

	Object getAllProductForPopular();

	List<Product> getAllProductByCategory(String category, Integer index, Integer limit);
	
	List<Product> getBestSeller(String category);

	CompProduct getCompactPStock(Long prod, Long branch);

	List<CompProduct> getCompactPStockByFirmProdType(Long firm, String ptype);

	List<CompProduct> getCompactPStock(Long firm);

	List<CompProduct> getCompactPStock();
	
	List<CompProduct> getCompactPStock(Long firm, String category);

	List<CompProduct> getCompactPStockByProdType(String ptype);

	CompProduct getCompactPStock(Long prod, Long firm, String doctype);

}
