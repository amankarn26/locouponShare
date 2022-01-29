package com.locoupon.common.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.ProductDao;
import com.locoupon.common.entity.ProdType;
import com.locoupon.common.entity.Product;
import com.locoupon.common.enums.EnumBoolean;
import com.locoupon.common.enums.EnumBoolean.type;
import com.locoupon.common.enums.RateUnit;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.model.DishDetailStats;


@Transactional
@Service
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

	@Override
	public List<Product> getAllProduct() {
		logger.debug("getAllProduct");
		String qlString = "from Product";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		return query.getResultList();
	}
	
	@Override
	public List<Product> getAllProduct(Integer index, Integer limit) {
		logger.debug("getAllProduct");
		String qlString = "from Product";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setFirstResult(index);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public List<Product> getAllProduct(String prodtype) {
		String qlString = "from Product where prodtype=:prodtype";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("prodtype", prodtype);
		return query.getResultList();
	}

	@Override
	public List<Product> getAllProductByCategory(String category) {
		String qlString = "from Product where prodtype IN (select name from ProdType where category=:category)";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("category", category);
		return query.getResultList();
	}

	@Override
	public List<Product> getProductByCategoryAndProdType(String category, String prodtype) {
		String qlString = "from Product where prodtype=:prodtype and prodtype IN (select name from ProdType where category=:category) order by priority, name";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("category", category);
		query.setParameter("prodtype", prodtype);
		return query.getResultList();
	}

	@Override
	public Product getProductById(Long id) {
		logger.debug("getProductById " + id);
		try {
			String qlString = "from Product where id=:id";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("id", id);

			Product Product = query.getSingleResult();
			return Product;
		} catch (Exception e) {
			logger.error("Exception in getProductById " + id, e);
			return null;
		}
	}

	
	@Override
	public ErrorCodes deleteProduct(Product dbProduct) {
		logger.debug("delete Product " + dbProduct.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbProduct);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Product " + dbProduct.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes updateProduct(Product dbProduct) {

		logger.debug("update Product " + dbProduct.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(dbProduct);
			// logger.debug("update Product success " + dbProduct.getId());
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Product " + dbProduct.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public ErrorCodes add(Product Product) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		// logger.debug("Saving Product " +Product.getId());
		try {
			entityManager.persist(Product);
			// logger.debug("Saving Product Success " +Product.getId());
		} catch (Exception e) {
			logger.debug("Exception in saving Product ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public Product checkIfProductExists(String name, ErrorCodes reqError) {
		logger.debug("checkIfProductExists " + name);

		Product prod = null;
		try {
			String qlString = "from Product where name=:name";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("name", name);

			prod = query.getSingleResult();
			if (null == prod) {
				reqError.setValue(ErrorCodes.PRODUCT_DOES_NOT_EXIST.toString());
				reqError = ErrorCodes.PRODUCT_DOES_NOT_EXIST;
				return null;
			} else {
				return prod;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfProductExists " + name + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.PRODUCT_DOES_NOT_EXIST.toString());
			reqError = ErrorCodes.PRODUCT_DOES_NOT_EXIST;
			return null;
		}
	}


	@Override
	public DishDetailStats getProductStats(Long id) {
		logger.debug("getProductStats");

		// return query.getResultList();
		List<Object[]> list = entityManager
				.createQuery("SELECT e.id, e.name, e.unitsinstock FROM Product e  where e.id=:id")
				.setParameter("id", id).getResultList();

		logger.debug("getProductStats Found:" + list.size());
		DishDetailStats pstat = new DishDetailStats();

		for (Object[] obj : list) {
			pstat.setProdid(Long.parseLong(obj[0].toString()));
			pstat.setPname(obj[1].toString());
			pstat.setInstockunits(Long.parseLong(obj[2].toString()));

		}
		return pstat;
	}

	@Override
	public ErrorCodes addProdType(ProdType menu) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.persist(menu);
		} catch (Exception e) {
			logger.debug("Exception in saving Product ", e);
			reqError.setValue("Hibernate Exception:" + e.getMessage());
			return reqError.HIBERNATE_EXCEPTION;
		}
		return reqError;
	}

	@Override
	public ErrorCodes deleteProdType(ProdType dbobj) {
		logger.debug("delete Product " + dbobj.getId());
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.remove(dbobj);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in delete Product " + dbobj.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}

	@Override
	public List<Product> getAllProductBySaletype(Long firm, EnumBoolean.type forsale, String prodtype) {

		logger.debug("getAllProduct" + forsale + prodtype);
		String qlString = "from Product where prodtype IN (select prodtype from ProdType where firm=:firm and forsale=:forsale and prodtype=:prodtype)";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("firm", firm);
		query.setParameter("forsale", forsale);
		query.setParameter("prodtype", prodtype);
		return query.getResultList();
	}

	@Override
	public List<Product> getAllProductForRateUnit() {
		logger.debug("getAllProduct");
		String qlString = "from Product";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		return query.getResultList();
	}

	@Override
	public ErrorCodes updateProdType(ProdType menu) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		try {
			entityManager.merge(menu);
			return reqError;
		} catch (Exception e) {
			logger.error("Exception in update Product " + menu.getId(), e);
			reqError = RequestTypes.ErrorCodes.HIBERNATE_EXCEPTION;
			return reqError;
		}
	}


	@Override
	public Product getProductByPnameAndRateunit(String name, RateUnit.type rateunit) {
		try {
			String qlString = "from Product where name=:name and rateunit=:rateunit";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("name", name);
			query.setParameter("rateunit", rateunit);

			Product Product = query.getSingleResult();
			return Product;
		} catch (Exception e) {
			logger.error("Exception in getProductByPnameAndRateunit " + name, e);
			return null;
		}
	}

	@Override
	public List<ProdType> getAllProdType(Integer index, Integer limit) {
		String qlString = "from ProdType";
		TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
		query.setFirstResult(index);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<ProdType> getAllProdType() {
		String qlString = "from ProdType";
		TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
		return query.getResultList();
	}

	@Override
	public ProdType getProdTypeById(Long id) {
		try {
			String qlString = "from ProdType where id=:id";
			TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
			query.setParameter("id", id);

			ProdType obj = query.getSingleResult();
			return obj;
		} catch (Exception e) {
			logger.error("Exception in getProdTypeById " + id, e);
			return null;
		}
	}

	@Override
	public List<ProdType> getAllProdTypeByCategory(String category) {
		String qlString = "from ProdType where category=:category";
		TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
		query.setParameter("category", category);
		return query.getResultList();
	}

	@Override
	public boolean checkIfMenuInUSe(String prodtype) {
		List<Product> Product = null;
		try {
			String qlString = "from Product where prodtype=:prodtype";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("prodtype", prodtype);
			Product = query.getResultList();
			
			if (Product.size() == 0) {
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfMenuInUSe Exception: " + e.getMessage());
			return true;
		}
		return true;

	}

	@Override
	public List<String> getAllProdTypeUnique() {
		String qlString = "select name from ProdType GROUP BY name order by priority";
		Query query = entityManager.createQuery(qlString);
		return query.getResultList();
	}

	@Override
	public Product getProductByName(String name) {
		try {
			String qlString = "from Product where name=:name";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("name", name);

			Product Product = query.getSingleResult();
			return Product;
		} catch (Exception e) {
			logger.error("Exception in getProductById " + name, e);
			return null;
		}
	}

	@Override
	public List<ProdType> getProdTypeByName(String name) {
		try {
			String qlString = "from ProdType where name=:name";
			TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
			query.setParameter("name", name);

			 
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Exception in ProdType " + name, e);
			return null;
		}
	}

	@Override
	public List<Product> getAllProductByCategory(String category, Integer index, Integer limit) {
		String qlString = "from Product where prodtype IN (select name from ProdType where category=:category)";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("category", category);
		query.setFirstResult(index);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List<Product> getBestSeller(String category) {
		logger.debug("getAllProductByCategoryFirm");
		String qlString = "from Product where prodtype IN (select name from ProdType where category=:category)";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("category", category);
		query.setMaxResults(8);
		return query.getResultList();
	}

	@Override
	public List<Product> getAllProductByProdtype(String prodtype) {
		logger.debug("getAllProductByprodtype");
		String qlString = "from Product where prodtype=:prodtype";
		TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
		query.setParameter("prodtype", prodtype);
		return query.getResultList();
	}

	@Override
	public ProdType checkIfProdTypeExist(String category, String name) {
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;
		logger.debug("checkIfProdTypeExist " + name);

		ProdType prod = null;
		try {
			String qlString = "from ProdType where name=:name and category=:category";
			TypedQuery<ProdType> query = entityManager.createQuery(qlString, ProdType.class);
			
			query.setParameter("name", name);
			query.setParameter("category", category);
			prod = query.getSingleResult();
			if (null == prod) {
				reqError.setValue(ErrorCodes.PRODTYPE_DOES_NOT_EXIST.toString());
				reqError = ErrorCodes.PRODTYPE_DOES_NOT_EXIST;
				return null;
			} else {
				return prod;
			}
		} catch (Exception e) {
			logger.error("Exception in checkIfProdTypeExist " + name + " Exception: " + e.getMessage());
			reqError.setValue(ErrorCodes.PRODTYPE_DOES_NOT_EXIST.toString());
			reqError = ErrorCodes.PRODTYPE_DOES_NOT_EXIST;
			return null;
		}
	}

	@Override
	public List<Product> getProductByPType(Long id, String prodtype) {
		try {
			String qlString = "from Product where id=:id and prodtype LIKE :prodtype order by name";
			TypedQuery<Product> query = entityManager.createQuery(qlString, Product.class);
			query.setParameter("id", id);
			query.setParameter("prodtype", '%' + prodtype + '%');
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Exception in getProductByPType " + prodtype, e);
			return null;
		}

	}

	


	

}