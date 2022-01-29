package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Category;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface CategoryService {

	List<Category> getAllCategory();

	ErrorCodes addCategory(Category firm, Request reqType);

	ErrorCodes deleteCategory(Category firm, Request reqType);

	Category getCategoryById(Long id);

	ErrorCodes addMultipleCategory(List<Category> catlist, Request reqType);

	Category validateUpdateCategory(Category firm, Request reqType, ErrorCodes reqError);

	ErrorCodes updateCategory(Category dbfirm, Request reqType);

	Category checkCategoryInUsed(Long id, ErrorCodes reqError);
	
	List<Category> getAllCategory(Integer index, Integer limit);

}
