package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Category;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


public interface CategoryDao {

	List<Category> getAllCategory();

	Category getCategoryById(Long id);

	ErrorCodes addCategory(Category firm);

	ErrorCodes deleteCategory(Category dbfirm);

	Category checkIfCategoryExists(String name, ErrorCodes reqError);

	ErrorCodes updateCategory(Category dbfirm);

	boolean checkCategoryInUsed(Long id, ErrorCodes reqError);

	List<Category> getAllCategory(Integer index, Integer limit);
	
}
