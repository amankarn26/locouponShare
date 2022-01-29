package com.locoupon.common.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.DishDetailStats;



@Transactional
@Service
public interface ProductDao {

	ErrorCodes add(Product product);

	ErrorCodes updateProduct(Product dbproduct);

	Product getProductById(Long id);

	List<Product> getAllProduct();
	
	List<Product> getAllProduct(Integer index, Integer limit);
	
	List<Product> getAllProduct(String menutype);

	List<Product> getAllProductByCategory(String category);

	List<Product> getProductByCategoryAndProdType(String category, String menutype);

	ErrorCodes deleteProduct(Product dbproduct);

	Product checkIfProductExists(String name, ErrorCodes reqError);

	DishDetailStats getProductStats(Long id);

	ErrorCodes addProdType(ProdType product);

	ErrorCodes deleteProdType(ProdType dbproduct);

	List<Product> getAllProductBySaletype(Long firm, EnumBoolean.type forsale,
			String prodtype);

	List<Product> getAllProductForRateUnit();

	ErrorCodes updateProdType(ProdType pt);

	Product getProductByPnameAndRateunit(String pname, RateUnit.type rateunit);

	List<ProdType> getAllProdType(Integer index, Integer limit);

	List<ProdType> getAllProdType();

	ProdType getProdTypeById(Long id);

	List<ProdType> getAllProdTypeByCategory(String category);

	boolean checkIfMenuInUSe(String name);

	List<String> getAllProdTypeUnique();

	Product getProductByName(String dname);

	List<ProdType> getProdTypeByName(String name);

	List<Product> getAllProductByCategory(String category, Integer index, Integer limit);

	List<Product> getBestSeller(String category);

	List<Product> getAllProductByProdtype(String dishtype);

	ProdType checkIfProdTypeExist(String category, String name);

	List<Product> getProductByPType(Long prodid, String ptype);

}