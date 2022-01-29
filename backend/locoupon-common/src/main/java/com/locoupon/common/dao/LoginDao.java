package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Login;
import com.locoupon.common.enums.LoginType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;


public interface LoginDao {

	ErrorCodes add(Login login);

	ErrorCodes delete(Login login);

	ErrorCodes update(Login dblog);

	Login get(Long id);

	Login verifylogin(String username, String password);

	Login getlogin(String username);

	List<Login> getLoginType(LoginType.type type);
}
